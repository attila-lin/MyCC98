package tk.djcrazy.MyCC98.adapter;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import tk.djcrazy.MyCC98.PmViewActivity;
import tk.djcrazy.MyCC98.R;
import tk.djcrazy.libCC98.data.PmInfo;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class PmListViewAdapter extends BaseItemListAdapter<PmInfo> {

	public PmListViewAdapter(Activity context, List<PmInfo> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	public final class ListItemView {
		public View pmItem;
		public TextView senderName;
		public TextView topic;
		public TextView time;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemView itemView = null;
		if (convertView == null) {
			itemView = new ListItemView();
			convertView = inflater.inflate(R.layout.pm_item, null);

			findViews(convertView, itemView);

			convertView.setTag(itemView);
		} else {
			itemView = (ListItemView) convertView.getTag();
		}

		fillDataIntoView(position, itemView);
		final int fpos = position;
		itemView.pmItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PmInfo pmInfo = items.get(fpos);
				reply_pm(pmInfo.getPmId(), pmInfo.getSender(),
						pmInfo.getSendTime(), pmInfo.getTopic());
			}
		});
		return convertView;
	}

	/**
	 * Reply to the pm with the pm id. Start the PmReply Activity.
	 * 
	 * @param pmId
	 */
	public void reply_pm(int pmId, String sender, String sendTime, String topic) {
		Intent intent = new Intent(context, PmViewActivity.class);
		intent.putExtra("PmId", pmId);
		intent.putExtra("Sender", sender);
		intent.putExtra("SendTime", sendTime);
		intent.putExtra("Topic", topic);
		context.startActivity(intent);
		context.overridePendingTransition(R.anim.forward_activity_move_in,
				R.anim.forward_activity_move_out);

	}

	/**
	 * find views in the pm list item view
	 * 
	 * @param convertView
	 * @param itemView
	 */
	private void findViews(View convertView, ListItemView itemView) {
		itemView.pmItem = convertView.findViewById(R.id.pm_item_view);
		itemView.senderName = (TextView) convertView
				.findViewById(R.id.pm_sender_name);
		itemView.topic = (TextView) convertView.findViewById(R.id.pm_topic);
		itemView.time = (TextView) convertView.findViewById(R.id.pm_time);
	}

	/**
	 * 
	 * @param position
	 * @param itemView
	 */
	private void fillDataIntoView(int position, ListItemView itemView) {
		// fill data into itemView
		itemView.senderName.setText(StringEscapeUtils.unescapeHtml4(items.get(
				position).getSender()));
		itemView.topic.setText(StringEscapeUtils.unescapeHtml4(items.get(
				position).getTopic()));
		itemView.time.setText(items.get(position).getSendTime());
	}
}
