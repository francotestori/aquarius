import akka.actor.Scheduler;
import play.Application;
import play.GlobalSettings;
import play.libs.Akka;
import scala.concurrent.duration.FiniteDuration;
import tasks.ProjectTerminationTask;

import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        //Declare in method
        final Scheduler scheduler = Akka.system().scheduler();

        //Schedule tasks
        scheduleProjectTermination(scheduler);
        scheduleAnotherTask(scheduler); //demo TODO: remove
        //...other task methods
    }

    private void scheduleProjectTermination(@NotNull Scheduler scheduler) {
        final FiniteDuration initialDelay = new FiniteDuration(100l, TimeUnit.DAYS); //Delay since application start
        final FiniteDuration interval = new FiniteDuration(100l, TimeUnit.DAYS); //Delay since last execution
        scheduler.schedule(initialDelay, interval, new ProjectTerminationTask(), Akka.system().dispatcher());
    }

    /**
     * Demo method
     * @param scheduler
     */
    private void scheduleAnotherTask(@NotNull Scheduler scheduler){
        //Do some scheduling!
    }
}
