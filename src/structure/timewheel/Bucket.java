package structure.timewheel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anur IjuoKaruKas on 2018/10/16
 */
public class Bucket {

    private List<TimedTask> timedTaskList = new ArrayList<>();

    public void addTask(TimedTask timedTask) {
        timedTaskList.add(timedTask);
    }

    public List<TimedTask> getTaskList() {
        return timedTaskList;
    }
}
