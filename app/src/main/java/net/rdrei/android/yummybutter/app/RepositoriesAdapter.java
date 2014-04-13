package net.rdrei.android.yummybutter.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RepositoriesAdapter extends GenericAdapter<RepositoriesAdapter.RepositoryEntity> {
    public RepositoriesAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(android.R.layout.simple_list_item_2, parent, false);

            final ViewHolder viewHolder = new ViewHolder(convertView);

            assert convertView != null;
            convertView.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        final RepositoryEntity item = getItem(position);

        viewHolder.title.setText(item.name);
        viewHolder.description.setText(item.description);

        return convertView;
    }

    public static class ViewHolder {
        @InjectView(android.R.id.text1)
        public TextView title;

        @InjectView(android.R.id.text2)
        public TextView description;

        public ViewHolder(final View view) {
            ButterKnife.inject(this, view);
        }
    }

    public static class RepositoryEntity {
        public String name;
        public String description;

        public RepositoryEntity(String title, String description) {
            this.name = title;
            this.description = description;
        }

        @Override
        public String toString() {
            return "RepositoryEntity{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
