# Dagger2_simple_example
Basic example of implementing dagger2 for dependency injection.

## Dagger2 (dependency injection):

- Official Defination: Dagger is a compile-time framework for dependency injection. It uses no reflection or
                      runtime bytecode generation, does all its analysis at compile-time, and generates plain Java source code.

- Project setup:
  
  https://github.com/google/dagger
   
   add dependency:
   
       implementation 'com.google.dagger:dagger:2.21'
       annotationProcessor 'com.google.dagger:dagger-compiler:2.21'
	   
      
	from this google dagger official github page.

- objects used by one object are called dependencies of that object.	  

- Exmaple:

	- SmartPhone ----> Battery
	-		       ----> MemoryCard
	-	       ----> SimCard ----> ServiceProvider
			   
- this smart phone object has three direct dependencies.
	SIMCard has one dependency, ServiceProvider.
	
- So, the ServiceProvider becomes and indirect dependency to the SmartPhone.
	
- Here in the main activity we construct these instances.
	
- That’s what we want to avoid with the support of dagger 2. In order to have a loosely coupled ,
 maintainable ,testable code we need to get them to the main activity form an outside source instead 
 of constructing them here.
 
- We should keep the reference variable of the
 SmartPhone.
 
- First of all dagger has to construct a service provider instance, and then
 Using that , Dangger can construct a SIM Card instance.

- Dagger has to also construct a Battery instance and a MemoryCard instance.

- After that, using Battery, MemoryCard and SIMCard instances dagger can construct a SmartPhone instance.

- We can use @Inject annotation to tell dagger to use a constructor to make the object ,if it is required to inject that object as a dependency.
 Let's start from the constructor of the SmartPhone class.

- This injection is somewhat recursive, if a inject annotated constructor has parameters, dagger will look
 for ways to inject them as well. SmartPhone constructor has 3 parameters.
 So we need to inject their constructors as well.

- This SIMCard has a ServiceProvider as a parameter .Let’s inject the constructor of it as well.

- What we just did here is call, constructor injection.

- When you are using dagger 2 for dependency injection, it is highly recommended to use constructor injection for every possible scenario.


- To use dagger 2 generated codes for dependency injection, we also need an interface annotated with @Component annotation.

- Let’s create a new interface. Name it as SmartPhoneComponent. Annotate the interface with @Component annotation.

- Now we can write an abstract method to get the dependency we want. The name of the method is not important.

- We can write any name as the method name. But the return type of the method should be the type of the dependency you want.

- Let’s name this method as getSmartPhone. Return type should be SmartPhone.


- When generating codes for dependency injection dagger will only consider this return type. Now,
 let’s go back to main activity. Let’s write codes to get the interface here.

- For starting, keeping it as null. Because dagger has not generate codes yet.

- By rebuilding the project we can get them generated by dagger . Let’s rebuild the project.

- Now if you check this generaedJava package, you will see newly generated codes by dagger considering the instructions
 provided by us using annotations. You can see dagger has created factory classes for each dependency.

- And for the component interface dagger has created a class named DaggerSmartPhoneComponnent implementing the interface.

- When generating a class which implements component interface, dagger always include the word
 Dagger in front of the interface name.

- So , now here in the main activity we can use DaggerSmartPhoneComponnent class.

- To get a SmartPhone dependency we definded a getter method here. And dagger has implemented that here. So we can use it.

- Now if you run this project you will see the result of this make call method invocation.

- Let’s run the app. Check the logcat. yes app is working as expected.

- So , that’s how we do dependency injection using dagger 2. We didn’t construct any dependency inside the MainActivity.

- Instead we constructed the out side using dagger 2 annotations and injected them to the main activity. 

- Remember to use @Inject annotation for all the constructors of the dependencies. And Remember to use a component
  interface annotated with component interface to tell dagger to construct those dependencies.


# Dagger Modules

- Whenever we are using dagger we should go with constructor injection for all classes you own.

- But what about classes where we cannot access the constructor, like when we are using a Retrofit,
 we build it using its builder method.

- Or when we can’t instantiate the dependency, like a context object. When we are using the classes
 we don’t own, classes form third party libraries, we cannot open the class and add
 @Inject annotation to the constructor.


- For this type of situations we can use modules and write provider methods to provide those dependencies.
  But remember, you should create modules only when you actually need them.

- Example:

 - Just for the demonstration of the concept, let’s assume that we don’t own this MemoryCard class.
   So we cannot add this @Inject annotation here.Let’s remove it.

 - Now I am creating a module class for this dependency.

 - Create a new java class. Name it as MemoryCardModule.

 - To make this a dagger module we need to annotate it with module annotation.

 - Now I am creating a provider method with the Return type of MemoryCard. Name it as provideMemoryCard.

 - You can give any name for the method. But we usually start the name with provide
   We can construct a MemoryCard instance and retrun it here.

 - We need to annotate this method with @provides annotation.

 - Marking a method with this annotation tells dagger, that this method provides the return data type.

 - Now open the SmartPhoneComponent interface. Here we should link our module to this component.

 - Let’s rebuild the project now.

 - If you run this project you will see that we are getting the same result.


 - So, This is the dependency graph of our small project.

      - SmartPhone ----> Battery
	-            ----> MemoryCard
	-	     ----> SimCard ----> ServiceProvider
- To construct SmartPhone dependency dagger has to first get other dependencies.

- Dagger construct 3 of them considering @inject annotated constructors.

- Dagger gets other one from the provider method of the MemoryCardModule.

- This module has only one provider method. But a module can have more than one provider methods.

- Module annotation is basically used on a class to grouping of similar types of @Provides methods together.

- Here , we can make this provider method as static. If the provider methods of the module
  does not depend on any instance variable of the module we can make those methods static.

- If your module only has static provide methods, Dagger will never need to instantiate them.
  So it will improve the performance.*/
  
---------------------------------------------********End Section2******----------------------------------------------------
 
 
# Working with Interfaces

- In some cases we may have Interface type as a dependency instead of a class. let’s show you how to work with interfaces:

- Here, I am changing this battery class to an interface.

- Let’s also add an abstract method to log the type of the battery.

- Now here in this SmartPhone’s constructor we have Battery as a parameter.

- But now this Battery is an interface. If we run this app now it will not work.Because at this point there is 
  no way to construct a battery.

- So We need to create a class which implements the battery interface and provide the dependency through a module.
  So let’s create a new java class. Name it as NickelCadmiumBattery

- This should implement the battery interface.

- Override the showType method. Here  am going to use the same tag we used for the smartphone class.
  Let’s log the type of the battery.

- And we should annotate the constructor with @Inject annotation. If we try to run the project
  now, Android studio will show an error message. Here we have a dependency of NickelCadmiumBattery. 
  As it has implemented Battery interface, we know this is a Battery. But dagger does not type cast for dependencies in that   way.
  We have to create a module and provide this dependency as a Battery dependency.

- So, let’s create a new java class for the module. Name it as NCBatteryModule.

- Annotate this with the @Module annotation. Let’s create a provider method now. Return type should be Battery.

- Name the method as provideNCBattery. We can just return a new Nickel Cadmium Battery interface instance in this way.

- But it is unnecessary. As we have annotated the constructor of the NickelCadmiumBattery with inject annotation 
  dagger can construct that dependency. So we can use that dependency here, we don’t have to construct it again.

- Now return the NickelCadmiumBattery instance. We can also invoke the showType() method before return the instance. 
  We should also annotate this provider method with the provides annotation Dagger recognizes this returned dependency 
  by considering the return type of the provider method. So dagger will recognize this dependency as a battery.

- Now let’s go back to the SmartPhoneComponent.

- Here we need to add, our newly created module to the modules list.

- Now, let’s run the app to see the results.

- Open the logcat and type the tag name. You can see both log values here. Our app is working as expected.

- "It is very easy you need to create a class which implements the interface and you need to provide it through a module".

- There is another way of doing this. You can also make this module abstract.

- We should also make this provider method abstract 

- An abstract method does not have a method body. So, let’s remove the method body.

- I am also changing this name from provide to bind. We also need to annotate this with binds annotation.

- We just removed the showType method call. But we need it to see the log results .

- Therefore, I am going to add that in the constructor of the SmartPhone class.

- Now we can run the app again and see how this works

- our app is working as expected.

---------------------------------------------********End Section3********---------------------------------------------------- 
