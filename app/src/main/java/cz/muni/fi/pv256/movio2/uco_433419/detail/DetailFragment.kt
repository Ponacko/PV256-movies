package cz.muni.fi.pv256.movio2.uco_433419.detail

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import cz.muni.fi.pv256.movio2.uco_433419.R
import cz.muni.fi.pv256.movio2.uco_433419.data.FilmManager
import cz.muni.fi.pv256.movio2.uco_433419.model.Film
import kotlinx.android.synthetic.main.fragment_detail.*
import java.text.SimpleDateFormat


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var manager: FilmManager
    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mParam1 = arguments?.getString(ARG_PARAM1)
        mParam2 = arguments?.getString(ARG_PARAM2)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_detail, container, false)

    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (getString(R.string.layout) != "large-land") {
            (activity as AppCompatActivity).supportActionBar?.hide()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    fun setFilmProperties(film: Film) {

        val parser = SimpleDateFormat("yyyy-MM-dd")
        val formatter = SimpleDateFormat("dd.MM.")
        val date = formatter.format(parser.parse(film.release_date))
        titleText.title = "${film.original_title} (${date})"
        filmDescription.text = film.overview
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w500" +
                        film.backdrop_path).into(filmImage)
        manager = FilmManager(context!!)
        if (manager.containsFilm(film)) {
            switchFabState(true, film)
        } else {
            switchFabState(false, film)
        }
    }

    private fun switchFabState(toAdded: Boolean, film: Film) {
        if (toAdded) {
            fab.setImageDrawable(resources.getDrawable(R.drawable.ic_remove_black_24dp))
            fab.setOnClickListener {
                manager.deleteFilm(film)
                switchFabState(false, film)
            }
        } else {
            fab.setImageDrawable(resources.getDrawable(R.drawable.ic_add_black_24dp))
            fab.setOnClickListener {
                manager.createFilm(film)
                switchFabState(true, film)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }


    }
}// Required empty public constructor
