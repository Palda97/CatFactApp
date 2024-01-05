import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class CommonLibraryModulePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.android<LibraryExtension> {
            commonCoreLibraryAndroidBlock()
            compose()
        }
        target.addKermitDependency()
    }
}
