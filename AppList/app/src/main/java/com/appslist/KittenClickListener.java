package com.appslist;

/**
 * Listener for kitten click events in the grid of kittens
 *
 * @author bherbst
 */
public interface KittenClickListener {
    /**
     * Called when a kitten is clicked
     * @param holder The ViewHolder for the clicked kitten
     * @param position The position in the grid of the kitten that was clicked
     */
    void onKittenClicked(KittenGridAdapter.KittenViewHolder holder, int position);
}
