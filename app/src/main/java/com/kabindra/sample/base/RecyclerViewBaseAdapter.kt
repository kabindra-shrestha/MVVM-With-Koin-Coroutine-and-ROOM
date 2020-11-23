package com.kabindra.sample.base

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.*
import com.kabindra.sample.R

abstract class RecyclerViewBaseAdapter<ITEM> : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    AutoUpdatableAdapter {

    abstract var itemLayoutId: Int
    abstract var items: List<ITEM>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)
        return RecyclerViewHolder(itemView)
    }

    fun addItems(newItems: List<ITEM>) {
        val list = items.toMutableList()
        list.addAll(newItems)
        items = list
    }

    fun remove(item: ITEM) {
        val list = items.toMutableList()
        list.remove(item)
        items = list
    }

    override fun getItemCount(): Int = items.size
}

interface OnRecyclerItemClick<T> {
    fun onItemClick(item: T, position: Int)
    fun onItemLongClick(item: T, position: Int)
}

fun RecyclerView.initRecycler(
    mContext: Context,
    addDivider: Boolean = false,
    spanCount: Int = 1,
    @AnimRes animRes: Int = R.anim.layout_animation_fall_down,
    spacing: Int = 0
) =
    this.apply {
        addLayoutManager(mContext, spanCount)
        addLayoutAnimation(animRes)
        if (addDivider) addDivider(mContext) else addEqualSpacing(spacing)
    }

fun RecyclerView.addLayoutAnimation(@AnimRes animRes: Int = R.anim.layout_animation_fall_down) {
    layoutAnimation = AnimationUtils.loadLayoutAnimation(context, animRes)
}

fun RecyclerView.addLayoutManager(context: Context, spanCount: Int = 1) {
    layoutManager = if (spanCount > 1) {
        GridLayoutManager(context, spanCount)
    } else {
        LinearLayoutManager(context)
    }
}

fun RecyclerView.addDivider(
    context: Context,
    dividerItemDecoration: Int = DividerItemDecoration.VERTICAL
) {
    addItemDecoration(DividerItemDecoration(context, dividerItemDecoration))
}

fun RecyclerView.addDividerHorizontal(
    context: Context,
    dividerItemDecoration: Int = DividerItemDecoration.HORIZONTAL
) {
    addItemDecoration(DividerItemDecoration(context, dividerItemDecoration))
}

fun RecyclerView.addEqualSpacing(spacing: Int = 16) {
    addItemDecoration(EqualSpacingItemDecoration(spacing))// 16px. In practice, you'll want to use getDimensionPixelSize
}

class RecyclerViewHolder constructor(view: View) : RecyclerView.ViewHolder(view)

interface AutoUpdatableAdapter {
    fun <T> RecyclerView.Adapter<*>.autoNotify(
        old: List<T>,
        new: List<T>,
        compare: (T, T) -> Boolean
    ) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return compare(old[oldItemPosition], new[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return old[oldItemPosition] == new[newItemPosition]
            }

            override fun getOldListSize() = old.size

            override fun getNewListSize() = new.size
        })
        diff.dispatchUpdatesTo(this)
    }
}

class EqualSpacingItemDecoration @JvmOverloads constructor(
    private val spacing: Int,
    private var displayMode: Int = -1
) : SpacingItemDecoration(spacing, spacing, spacing, spacing)

open class SpacingItemDecoration @JvmOverloads constructor(
    private val spacingTop: Int,
    private val spacingBottom: Int,
    private val spacingLeft: Int = 0,
    private val spacingRight: Int = 0,
    private var displayMode: Int = -1
) : RecyclerView.ItemDecoration() {

    //https://gist.github.com/alexfu/f7b8278009f3119f523a
    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
        const val GRID = 2
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildViewHolder(view).adapterPosition
        val itemCount = state.itemCount
        val layoutManager = parent.layoutManager
        if (layoutManager != null) setSpacingForDirection(
            outRect,
            layoutManager,
            position,
            itemCount
        )
    }

    private fun setSpacingForDirection(
        outRect: Rect,
        layoutManager: RecyclerView.LayoutManager,
        position: Int,
        itemCount: Int
    ) {

        // Resolve display mode automatically
        if (displayMode == -1)
            displayMode = when {
                layoutManager is GridLayoutManager -> GRID
                layoutManager.canScrollHorizontally() -> HORIZONTAL
                else -> VERTICAL
            }


        when (displayMode) {
            HORIZONTAL -> {
                outRect.left = spacingLeft
                outRect.right = if (position == itemCount - 1) spacingRight else 0
                outRect.top = spacingTop
                outRect.bottom = spacingBottom
            }
            VERTICAL -> {
                outRect.left = spacingLeft
                outRect.right = spacingRight
                outRect.top = spacingTop
                outRect.bottom = if (position == itemCount - 1) spacingBottom else 0
            }
            GRID -> if (layoutManager is GridLayoutManager) {
                val cols = layoutManager.spanCount
                val rows = itemCount / cols

                outRect.left = spacingLeft
                outRect.right = if (position % cols == cols - 1) spacingRight else 0
                outRect.top = spacingTop
                outRect.bottom = if (position / cols == rows - 1) spacingBottom else 0
            }
        }
    }
}

enum class RecyclerViewDataType(val type: Int, val desc: String) {
    TYPE_HEADER(1, "Header"),
    TYPE_DATA(2, "Data"),
    TYPE_FOOTER(3, "Footer")
}
