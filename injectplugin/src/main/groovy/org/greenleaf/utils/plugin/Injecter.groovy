package org.greenleaf.utils.plugin

import groovy.io.FileType
import javassist.ClassPool
import javassist.CtClass
import javassist.CtConstructor
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

/**
 * Created by davy on 2017/6/8.
 */
public class Injecter {

    private static final ClassPool mPool = ClassPool.getDefault()
    private static
    final String injectStr = "System.out.println(\"Injecter Helper\");"

    public static void injectDir(final Project project, final String rootPath, final List<String> injectPackages) {

        println "injectDir path:" + rootPath + " injectPackages:" + injectPackages
        println project.android.bootClasspath[0].toString()

        mPool.appendClassPath(project.android.bootClasspath[0].toString())
        mPool.appendClassPath(rootPath)

        File dir = new File(rootPath)
        if (!dir.isDirectory()) {
            return
        }

        final Set<String> injectPackagesPathSet = new HashSet<>()
        for (String packageNameItem : injectPackages) {
            injectPackagesPathSet.add(packageNameItem.replace(".", File.separator))
        }
        println injectPackagesPathSet

        dir.eachFileRecurse(FileType.FILES, { File file ->

            println()
            println("each file")
            String classFilePath = file.absolutePath
            String subClassFilePath = classFilePath.substring(rootPath.length() + 1)
            println subClassFilePath

            //确保当前文件是class文件，并且不是系统自动生成的class文件
            if (classFilePath.endsWith(".class")
                    && !classFilePath.contains('R$')
                    && !classFilePath.contains('R.class')
                    && !classFilePath.contains("BuildConfig.class")) {

                boolean needInject = false

                int index = -1

                for (String packageNameItem : injectPackagesPathSet) {
                    index = subClassFilePath.lastIndexOf(packageNameItem)
                    println packageNameItem
                    println subClassFilePath
                    println index
                    needInject = index != -1
                    if (needInject) {
                        break
                    }
                }

                println "injectDir process File fileName:" + file.getName() + " inject:" + needInject

                if (needInject) {

                    //subClassFilePath .aa.bb.cc.class
                    int end = subClassFilePath.length() - 6 // .class = 6
                    String className = subClassFilePath.substring(index, end)
                            .replace('\\', '.').replace('/', '.')

                    println "injectDir do inject className:" + className

                    //开始修改class文件
                    CtClass c = mPool.getCtClass(className)

                    if (c.isFrozen()) {
                        c.defrost()
                    }

                    CtConstructor[] cts = c.getDeclaredConstructors()
                    if (cts == null || cts.length == 0) {
                        //手动创建一个构造函数
                        CtConstructor constructor = new CtConstructor(new CtClass[0], c)
                        constructor.insertBeforeBody(injectStr)
                        c.addConstructor(constructor)
                    } else {
                        cts[0].insertBeforeBody(injectStr)
                    }
                    c.writeFile(rootPath)
                    c.detach()
                }
            }
        }
        )
    }

    /**
     * 这里需要将jar包先解压，注入代码后再重新生成jar包
     * @path jar包的绝对路径
     */
    public static void injectJar(String path) {
        if (path.endsWith(".jar")) {
            File jarFile = new File(path)

            // jar包解压后的保存路径
            String jarZipDir = jarFile.getParent() + "/" + jarFile.getName().replace('.jar', '')

            // 解压jar包, 返回jar包中所有class的完整类名的集合（带.class后缀）
            List classNameList = JarZipUtil.unzipJar(path, jarZipDir)

            // 删除原来的jar包
            jarFile.delete()

            // 注入代码
            mPool.appendClassPath(jarZipDir)
            for (String className : classNameList) {
                if (className.endsWith(".class")
                        && !className.contains('R$')
                        && !className.contains('R.class')
                        && !className.contains("BuildConfig.class")) {
                    className = className.substring(0, className.length() - 6)
                    injectClass(className, jarZipDir)
                }
            }

            // 从新打包jar
            JarZipUtil.zipJar(jarZipDir, path)

            // 删除目录
            FileUtils.deleteDirectory(new File(jarZipDir))
        }
    }
}