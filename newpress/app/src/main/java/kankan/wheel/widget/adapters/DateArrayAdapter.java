package kankan.wheel.widget.adapters;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DateArrayAdapter extends ArrayWheelAdapter<String> {
	// Index of current item
	int currentItem;
	// Index of item to be highlighted
	int currentValue;

	/**
	 * Constructor
	 */
	public DateArrayAdapter(Context context, String[] items, int current) {
		super(context, items);
		this.currentValue = current;
		setTextSize(16);
	}

	@Override
	protected void configureTextView(TextView view) {
		super.configureTextView(view);
		if (currentItem == currentValue) {
			view.setTypeface(Typeface.SANS_SERIF);
			view.setTextColor(Color.parseColor("#000000"));
			view.setTextSize(20);
		}

	}

	@Override
	public View getItem(int index, View cachedView, ViewGroup parent) {
		currentItem = index;
		return super.getItem(index, cachedView, parent);
	}
}
