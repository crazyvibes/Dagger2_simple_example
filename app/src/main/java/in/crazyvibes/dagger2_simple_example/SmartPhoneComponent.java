package in.crazyvibes.dagger2_simple_example;

import dagger.Component;

@Component(modules = {MemoryCardModule.class,NCBatteryModule.class})
public interface SmartPhoneComponent {

    //Abstract method

   SmartPhone getSmartPhone();
}
