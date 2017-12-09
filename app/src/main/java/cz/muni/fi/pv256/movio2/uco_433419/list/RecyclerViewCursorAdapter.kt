package cz.muni.fi.pv256.movio2.uco_433419.list

import android.database.Cursor
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.util.SortedListAdapterCallback
import android.view.View
import java.util.*


/** https://github.com/qvga/RecyclerViewCursorAdapter  */
abstract class RecyclerViewCursorAdapter<T, VH : RecyclerViewCursorAdapter.ViewHolder> : RecyclerView.Adapter<VH> {

    private var sortedList: SortedList<T>? = null
    private var previousCursorContent: Set<T> = HashSet()

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    constructor() : super() {}

    /** If you just need a plain sortedlist  */
    constructor(klass: Class<T>, cursor: Cursor?) {

        setSortedList(SortedList(klass, object : SortedListAdapterCallback<T>(this) {
            override fun compare(o1: T, o2: T): Int = 0

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = false

            override fun areItemsTheSame(item1: T, item2: T): Boolean = false
        }))
        setCursor(cursor)
    }


    fun setCursor(cursor: Cursor?) {
        switchCursor(cursor)
    }

    fun switchCursor(cursor: Cursor?) {
        if (cursor == null) {
            return
        }
        val currentCursorContent = HashSet<T>()
        sortedList?.beginBatchedUpdates()
        cursor.moveToPosition(-1)
        while (cursor.moveToNext()) {
            val item = fromCursorRow(cursor)
            currentCursorContent.add(item)
            sortedList?.add(item)
        }

        previousCursorContent
                .filterNot { currentCursorContent.contains(it) }
                .forEach { sortedList?.remove(it) }
        sortedList?.endBatchedUpdates()
        previousCursorContent = currentCursorContent
    }

    internal abstract fun fromCursorRow(cursor: Cursor): T

    internal fun setSortedList(list: SortedList<T>) {
        this.sortedList = list
    }

    override fun getItemCount(): Int = if (sortedList != null) sortedList!!.size() else 0

    fun getItem(position: Int): T? = sortedList?.get(position)

}