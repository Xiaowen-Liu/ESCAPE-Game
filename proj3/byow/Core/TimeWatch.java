package byow.Core;

import java.util.Timer;
import java.util.TimerTask;

public class TimeWatch {
    Timer timer;
    GenerateWorld map;

    public static void main(String[] args) {
        //TimeWatch x = new TimeWatch(2,);
    }

    TimeWatch(GenerateWorld background) {
        timer = new Timer();
        map = background;

    }

    public void startWatch(int seconds) {
        timer.schedule(new TimerTask() {
            int me = seconds;
            @Override
            public void run() {
                if (me > 0) {
                    map.time -= 1;
                    me--;
                } else {
                    timer.cancel();
                }
            }
        }, 0,  1000);
    }


}
