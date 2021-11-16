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

    protected static PsqlConnectionManager getInstance() {
        if (instance == null) {
            instance = new PsqlConnectionManager();
        } else if (instance.getSf().isClosed()) {
            instance = new PsqlConnectionManager();
        }
        return instance;
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
