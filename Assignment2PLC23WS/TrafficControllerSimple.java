package Assignment2PLC23WS;

import java.util.LinkedList;

public class TrafficControllerSimple implements TrafficController {
   private TrafficRegistrar registrar;   
   private boolean curVeh = true;
   private final Object obj = new Object();
   private int counter = 0;


   public TrafficControllerSimple(TrafficRegistrar registrar) {
       this.registrar = registrar;
   }

   @Override
   public void enterRight(Vehicle v) {
       synchronized (obj) {

           while (!curVeh || counter > 2)  {
               
               try {
                   obj.wait();
               } catch (InterruptedException e) {
                   Thread.currentThread().interrupt();
               }

           }
           counter++;
           if(counter == 2)
           curVeh = false;
           registrar.registerRight(v);

        //   this.leaveLeft(v);
       }

   }

   @Override
   public void enterLeft(Vehicle v) {
       synchronized (obj) {

           while (!curVeh || counter > 0) {
               
               try {
                   obj.wait();
               } catch (InterruptedException e) {
                   Thread.currentThread().interrupt();
               }

           }


            registrar.registerLeft(v);
           curVeh = false; 

           
          // this.leaveRight(v);
       }

   }

   @Override
   public void leaveLeft(Vehicle v) {
       synchronized (obj) {
           --counter;
           if(counter == 0)
        	   curVeh = true;
               registrar.deregisterRight(v);
               obj.notifyAll(); 
          // counter = 0;

       }

   }

   @Override
   public void leaveRight(Vehicle v) {
       synchronized (obj) {
           if (!curVeh ) {
               curVeh = true;
               registrar.deregisterLeft(v);
               obj.notifyAll(); 
           }

       }

   }


}
