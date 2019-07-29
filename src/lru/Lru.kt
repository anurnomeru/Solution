package lru

import java.util.HashMap

/**
 * Created by Anur IjuoKaruKas on 2019/7/24
 */
class Lru {
    val inner: HashMap<Any, Any> = HashMap()

    class Wrapper(val value: Any) {
        private var hitTimes = 0
        private var timeStamp = 0L

        fun hit() {
            hitTimes++
        }
    }

}