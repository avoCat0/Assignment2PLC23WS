package Assignment2PLC23WS;


import java.util.concurrent.locks.ReentrantLock;

public class TrafficControllerFair implements TrafficController {
    private TrafficRegistrar registrar;

    private static final ReentrantLock rLock = new ReentrantLock(true);

    public TrafficControllerFair(TrafficRegistrar r) {
        this.registrar = r;
    }

    public void enterRight(Vehicle v) {
        rLock.lock();
        registrar.registerRight(v);
    }

    public void enterLeft(Vehicle v) {
        rLock.lock();
        registrar.registerLeft(v);

    }

    public void leaveLeft(Vehicle v) {
        registrar.deregisterLeft(v);
        rLock.unlock();
    }

    public void leaveRight(Vehicle v) {
        registrar.deregisterRight(v);
        rLock.unlock();
    }
}