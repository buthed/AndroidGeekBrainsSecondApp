package com.tematikhonov.androidgeekbrainssecondapp.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tematikhonov.androidgeekbrainssecondapp.R
import com.tematikhonov.androidgeekbrainssecondapp.data.*
import com.tematikhonov.androidgeekbrainssecondapp.ui.DialogBuilderFragment.DeleteDialogCaller

class NotesListFragment : Fragment(), DeleteDialogCaller {
    private var recyclerView: RecyclerView? = null
    private var adapter: MyAdapter? = null
    private var data: CardsSource? = null
    private val navigation: Navigation? = null
    private val publisher: Publisher? = null
    private var moveToFirstPosition = false

    fun newInstance(): NotesListFragment? {
        return NotesListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notes_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        initRecyclerView()
        setHasOptionsMenu(true)
        data = CardSourceFirebaseImp().init(object : CardsSourceResponce {
            override fun initializes(cardsSource: CardsSource?) {
                adapter!!.notifyDataSetChanged()
            }
        })
        adapter!!.setDataSource(data)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return onItemSelected(item.itemId) || super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.notes_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return onItemSelected(item.itemId) || super.onContextItemSelected(item)
    }

    private fun onItemSelected(menuItemId: Int): Boolean {
        when (menuItemId) {
            R.id.action_add -> {
                navigation!!.addFragment(NoteFragment.newInstance(), true)
                publisher!!.subscribe(object : Observer {
                    override fun updateCardData(note: Note?) {
                        data!!.addCardData(note)
                        adapter!!.notifyItemInserted(data!!.size() - 1)
                        moveToFirstPosition = true
                    }
                })
                return true
            }
            R.id.action_update -> {
                val updatePosition = adapter!!.menuPosition
                navigation!!.addFragment(NoteFragment.newInstance(data!!.getNote(updatePosition)), true)
                publisher!!.subscribe(object : Observer {
                    override fun updateCardData(note: Note?) {
                        data!!.updateCardData(updatePosition, note)
                        adapter!!.notifyItemChanged(updatePosition)
                    }
                })
                return true
            }
            R.id.action_delete -> {
                val deletePosition = adapter!!.menuPosition
                val dialogFragment = DialogBuilderFragment()
                dialogFragment.setTargetFragment(this, 0)
                dialogFragment.show(requireActivity().supportFragmentManager, null)
                return true
            }
            R.id.action_clear -> {
                data!!.clearCardData()
                adapter!!.notifyDataSetChanged()
                return true
            }
        }
        return false
    }

    private fun initRecyclerView() {
        recyclerView!!.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        adapter = MyAdapter(this)
        recyclerView!!.adapter = adapter
        recyclerView!!.itemAnimator = null
        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator, null))
        recyclerView!!.addItemDecoration(itemDecoration)
        val animator = DefaultItemAnimator()
        animator.addDuration = 1000
        animator.removeDuration = 1000
        recyclerView!!.itemAnimator = animator
        if (moveToFirstPosition && data!!.size() > 0) {
            recyclerView!!.scrollToPosition(0)
            moveToFirstPosition = false
        }
        adapter!!.listener = object : MyAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("DreanNotes", "position =$position")
                Toast.makeText(context, String.format("Позиция - ", position), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDialogResult(result: String) {
        val position = adapter!!.menuPosition
        if (result == "DELETE") {
            val deletePosition = adapter!!.menuPosition
            data!!.deleteCardData(deletePosition)
            adapter!!.notifyItemRemoved(deletePosition)
        } else if (result == "CANCEL") {
            Toast.makeText(context, "Отклонено удаление заметки $position", Toast.LENGTH_SHORT).show()
        }
    }
}