package com.example.mercadoesclavoentregable.view.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mercadoesclavoentregable.databinding.ImageSlideCeldaBinding;
import com.example.mercadoesclavoentregable.model.Producto;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<Producto> picturesList = new ArrayList<>();
    private SliderView sliderView;

    public SliderAdapter(List<Producto> picturesList) {
        this.picturesList = picturesList;
    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {

        LayoutInflater infladorLayout = LayoutInflater.from(parent.getContext());

        ImageSlideCeldaBinding binding = ImageSlideCeldaBinding.inflate(infladorLayout, parent, false);


        return new SliderAdapterVH(binding);


    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {


        String imagenURL = picturesList.get(position).getSecureUrl();
        viewHolder.onBind(imagenURL);

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return picturesList.size();
    }

    protected class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        private ImageSlideCeldaBinding binding;

        View itemView;
        ImageView imageViewBackground;


        public SliderAdapterVH(ImageSlideCeldaBinding binding) {
            super(binding.getRoot());


            //imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);


            this.binding = binding;
        }

        public void onBind(String secureURL) {

            Glide.with(binding.celdaImageSlideImageView.getContext())
                    .load(secureURL)
                    .fitCenter()
                    .into(binding.celdaImageSlideImageView);
        }
    }

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<Producto> picturesList) {
        this.picturesList = picturesList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.picturesList.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Producto picturesList) {
        this.picturesList.add(picturesList);
        notifyDataSetChanged();
    }
}

