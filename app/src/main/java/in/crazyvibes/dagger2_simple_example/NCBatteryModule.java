package in.crazyvibes.dagger2_simple_example;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

//@Module
//public class NCBatteryModule {
//
//    @Provides
//    Battery provideNcBattery(NickelCadmiumBattery nickelCadmiumBattery){
//        nickelCadmiumBattery.showType();
//        return nickelCadmiumBattery;
//    }


   // other way of doing this by abstract class

@Module
abstract class NCBatteryModule {

    @Binds
   abstract Battery bindNcBattery(NickelCadmiumBattery nickelCadmiumBattery);

    //we have to show the result, so going to implement method in smartPhoneClass.

}
