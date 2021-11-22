package todo.store.psql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class PsqlConnectionManager implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger(PsqlConnectionManager.class);
    private static PsqlConnectionManager instance;
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    private PsqlConnectionManager() {
    }

    private static final class Lazy {
        private static final PsqlConnectionManager INST = new PsqlConnectionManager();
    }

    protected static PsqlConnectionManager instOf() {
        return Lazy.INST;
    }

    protected StandardServiceRegistry getRegistry() {
        return registry;
    }

    protected SessionFactory getSf() {
        return sf;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
