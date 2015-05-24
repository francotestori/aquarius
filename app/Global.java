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

    }

    private void scheduleProjectTermination(@NotNull Scheduler scheduler) {
        final FiniteDuration initialDelay = new FiniteDuration(0l, TimeUnit.DAYS); //Delay since application start
        final FiniteDuration interval = new FiniteDuration(1l, TimeUnit.DAYS); //Delay since last execution
        scheduler.schedule(initialDelay, interval, new ProjectTerminationTask(), Akka.system().dispatcher());
    }

}
