package in.crazyvibes.dagger2_simple_example;

import dagger.Module;
import dagger.Provides;

@Module
public class MemoryCardModule {

    @Provides                           //Marking a method with this annotation tells dagger, that this method provides the return data type.
    MemoryCard provideMemoryCard()
    {
        return new MemoryCard();
    }

}
