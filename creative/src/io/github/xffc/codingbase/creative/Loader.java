package io.github.xffc.codingbase.creative;

import com.google.gson.Gson;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class Loader implements PluginLoader {
    @Override
    public void classloader(PluginClasspathBuilder builder) {
        var resolver = new MavenLibraryResolver();

        Libraries libraries;
        try (var stream = getClass().getResourceAsStream("/paper-libraries.json")) {
            assert stream != null;
            var reader = new InputStreamReader(stream);
            libraries = new Gson().fromJson(reader, Libraries.class);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        libraries.repositories().entrySet().stream().forEach(e -> resolver.addRepository(
                new RemoteRepository.Builder(e.getKey(), "default", e.getValue()).build()
        ));

        libraries.dependencies().stream().forEach(d -> resolver.addDependency(
                new Dependency(new DefaultArtifact(d), null)
        ));

        builder.addLibrary(resolver);
    }

    private record Libraries(Map<String, String> repositories, List<String> dependencies) {}
}
