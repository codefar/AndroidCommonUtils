package org.greenleaf.utils.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

/**
 * Created by davy on 2017/6/8.
 */

public class InjectTransform extends Transform {

    Project project

    InjectTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return this.class.simpleName
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<QualifiedContent.ContentType> getOutputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        println this.class.simpleName + "transform start"

        // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
        transformInvocation.inputs.each { TransformInput input ->
            //对类型为“文件夹”的input进行遍历
            input.directoryInputs.each { DirectoryInput directoryInput ->
                //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
                Injecter.injectDir(directoryInput.file.absolutePath, "org/davy/commons/samples")
                // 获取output目录
                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)

                println "Directory:" + directoryInput.name + " " +  directoryInput.contentTypes + " " + directoryInput.file.getAbsolutePath()
                println " Output:" + dest.getAbsolutePath()
                // 将input的目录复制到output指定目录
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
            //对类型为jar文件的input进行遍历
            input.jarInputs.each { JarInput jarInput ->

                //jar文件一般是第三方依赖库jar文件
                // 重命名输出文件（同目录copyFile会冲突）
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())

                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                //生成输出路径
                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                //将输入内容复制到输出

                println "JAR:" + jarName + " " + jarInput.contentTypes + " " + jarInput.file.getAbsolutePath() + " " + md5Name
                println " Output:" + dest.getAbsolutePath()
                FileUtils.copyFile(jarInput.file, dest)
            }
        }

        println this.class.simpleName +  " transform end"
    }
}
