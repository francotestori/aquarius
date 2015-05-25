import akka.actor.Scheduler;
import play.Application;
import play.GlobalSettings;
import play.libs.Akka;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.FiniteDuration;
import tasks.DbPopulatorTask;
import tasks.ProjectTerminationTask;

import java.util.concurrent.TimeUnit;

public class Global extends GlobalSettings {
    private Scheduler scheduler;
    private ExecutionContextExecutor contextExecutor;

    @Override
    public void onStart(Application app) {
        //Initialize scheduler + context
        scheduler = Akka.system().scheduler();
        contextExecutor = Akka.system().dispatcher();

        //Schedule tasks
        scheduleDbPopulation();
        scheduleProjectTermination();

    }

    private void scheduleProjectTermination() {
        final FiniteDuration initialDelay = new FiniteDuration(0l, TimeUnit.DAYS);
        final FiniteDuration interval = new FiniteDuration(1l, TimeUnit.DAYS);
        scheduler.schedule(initialDelay, interval, new ProjectTerminationTask(), contextExecutor);
    }

    private void scheduleDbPopulation(){
        final FiniteDuration initialDelay = new FiniteDuration(0l, TimeUnit.DAYS);
        scheduler.scheduleOnce(initialDelay, new DbPopulatorTask(), contextExecutor);
    }

}
