package org.greatbarrierreeve.daizoubu.ui.common;


import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CollapsingCardManager {

    private final NestedScrollView scrollView;
    private final List<CardEntry>  entries = new ArrayList<>();


    // data model for card entries
    private static class CardEntry {

        final MaterialCardView card;
        final FrameLayout wrapper;
        int originalHeight = -1;
        int topInContent = -1;


        CardEntry(MaterialCardView card, FrameLayout wrapper) {

            this.card = card;
            this.wrapper = wrapper;

        }


        // check if card already captured to guard against re-capture
        boolean isReady() { return originalHeight != -1; }


        // store frame layout dimensions
        void capture() {

            if (!isReady() && wrapper.getHeight() > 0) {

                // store original height from frame layout
                originalHeight = wrapper.getHeight();

                // store frame layout position in parent
                topInContent = wrapper.getTop();

            }

        }

    }

    public CollapsingCardManager(@NonNull NestedScrollView scrollView) {

        this.scrollView = scrollView;

        // make ready cards collapsible on global layout state update
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {

            int scrollY = scrollView.getScrollY();
            for (CardEntry entry : entries) {

                entry.capture();
                if (entry.isReady()) applyCollapse(entry, scrollY);

            }

        });

        // make ready cards collapsible on scroll event
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            for (CardEntry entry : entries) {

                if (entry.isReady()) applyCollapse(entry, scrollY);

            }

        });

    }


    public void addCard(@NonNull MaterialCardView card) {

        // get position of card in parent
        ViewGroup parent = (ViewGroup) card.getParent();
        int index = parent.indexOfChild(card);

        // create frame layout to hold card original layout space
        FrameLayout wrapper = new FrameLayout(card.getContext());

        // copy card layout params to frame layout
        ViewGroup.LayoutParams originalParams = card.getLayoutParams();
        parent.removeView(card);
        parent.addView(wrapper, index, originalParams);

        // move card into frame layout
        wrapper.addView(card, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        // register card entry
        CardEntry entry = new CardEntry(card, wrapper);
        entries.add(entry);

        // enqueues card capture
        card.post(() -> {

            entry.capture();
            if (entry.isReady()) applyCollapse(entry, scrollView.getScrollY());

        });

    }


    private void applyCollapse(CardEntry entry, int scrollY) {

        // stores pixels of top edge beyond viewport top
        int scrolledPast = scrollY - entry.topInContent;

        // original state if card is fully within viewport
        if (scrolledPast <= 0) {

            setCardHeight(entry, entry.originalHeight);
            entry.card.setTranslationY(0f);

            // fully collapsed if card is fully scrolled past viewport
        } else if (scrolledPast >= entry.originalHeight) {

            setCardHeight(entry, 0);
            entry.card.setTranslationY(0f);

            // otherwise partially collapsed
        } else {

            // pin card top to viewport top
            setCardHeight(entry, entry.originalHeight - scrolledPast);

            // shrink card height
            entry.card.setTranslationY(scrolledPast);

        }

    }


    public void detach() {

        // restores all card entries to original state
        for (CardEntry entry : entries) { restore(entry); }

        // clean up list of entries
        entries.clear();

        // clean up scroll listener
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) null);

    }


    public void removeCard(@NonNull MaterialCardView card) {

        // use iterator to safely delete from list
        Iterator<CardEntry> it = entries.iterator();
        while (it.hasNext()) {

            CardEntry entry = it.next();
            if (entry.card == card) {

                restore(entry);
                it.remove();
                return;

            }

        }

    }


    private void restore(CardEntry entry) {

        // reset card to original state if previously captured
        if (entry.isReady()) {

            setCardHeight(entry, entry.originalHeight);
            entry.card.setTranslationY(0f);

        }

    }


    private void setCardHeight(CardEntry entry, int height) {

        // skip if height unchanged
        ViewGroup.LayoutParams params = entry.card.getLayoutParams();
        if (params.height == height) return;

        // update card height
        params.height = height;
        entry.card.setLayoutParams(params);

    }

}