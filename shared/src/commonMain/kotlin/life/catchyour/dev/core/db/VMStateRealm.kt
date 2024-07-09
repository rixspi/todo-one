package life.catchyour.dev.core.db

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class VMStateRealm: RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var stateKey: String = ""
    var serializedState: String = ""
}