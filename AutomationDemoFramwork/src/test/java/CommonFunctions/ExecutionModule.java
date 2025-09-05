package CommonFuncitons;

import com.google.inject.AbstractModule;
import CommonFunctions.ExecutionContext;

public class ExecutionModule extends AbstractModule {
    private final ExecutionContext context;

    public ExecutionModule(ExecutionContext context) {
        this.context = context;
    }

    @Override
    protected void configure() {
        bind(ExecutionContext.class).toInstance(context);
    }
}