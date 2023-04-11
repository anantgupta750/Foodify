package com.food.factory.Chat;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.food.factory.R;

import java.io.File;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{

    public int type;
//    SessionManager sessionManager;
    Context context;
    ArrayList<ChatModel> arrayList;
    public ChatAdapter(Context activity, ArrayList<ChatModel> myArraylist) {
        context = activity;
        arrayList = myArraylist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {

            case ChatModel.RECEIVE_MSG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatmsg, parent, false);
                return new RecivieTypeViewHolder(view);

            case ChatModel.SEND_MESSAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_send, parent, false);
                return new SendTypeViewHolder(view);
//            case ChatModel.SEND_IMAGE:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_msg, parent, false);
//                return new SendImageTypeViewHolder(view);
//            case ChatModel.RECEIVE_IMAGE:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recive, parent, false);
//                return new ReciveImageTypeViewHolder(view);

//            case ChatModel.SEND_PDF:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_pdf, parent, false);
//                return new SendPDFTypeViewHolder(view);

        }
        return  null;
    }


    @Override
    public int getItemViewType(int position) {

        switch (arrayList.get(position).type) {
            case 0:
                return ChatModel.SEND_MESSAGE;
            case 1:
                return ChatModel.RECEIVE_MSG;
//            case 2:
//                return ChatModel.SEND_IMAGE;
//            case 3:
//                return ChatModel.RECEIVE_IMAGE;
//            case 4:
//                return ChatModel.SEND_PDF;
            default:
                return position;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        context = holder.itemView.getContext();

        ChatModel object = arrayList.get(position);
        if (object != null) {
            switch (object.type) {

                case ChatModel.SEND_MESSAGE:
                    ((SendTypeViewHolder) holder).sendMsg.setText(object.getEmail());
                    ((SendTypeViewHolder) holder).sendTime.setText(object.getTime());
                    break;
                case ChatModel.RECEIVE_MSG:
                    Log.e("assaas", "onBindViewHolder: s" );
                    ((RecivieTypeViewHolder) holder).type.setText(object.getEmail());
                    ((RecivieTypeViewHolder) holder).timeDoctorSidePt.setText(object.getTime());
                    break;
//                case ChatModel.SEND_IMAGE:
//                    Log.e("assaas", "onBindViewHolder: Image" );
//
//                    Glide.with(context)
//                            .load(object.getEmail())
//                            .transition(DrawableTransitionOptions.withCrossFade())
//                            .into(((SendImageTypeViewHolder) holder).imgTypeSend);
//
//                    ((SendImageTypeViewHolder) holder).imgTypeSend.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(context, ImagePreview.class);
//                            intent.putExtra("url", object.getEmail());
//                            context.startActivity(intent);
//
//                        }
//                    });
//                    break;
//                case ChatModel.RECEIVE_IMAGE:
//                    Log.e("assaas", "onBindViewHolder: Image" );
//
//                    Glide.with(context)
//                            .load(object.getEmail())
//                            .transition(DrawableTransitionOptions.withCrossFade())
//                            .into(((ReciveImageTypeViewHolder) holder).imgTyperecive);
//
//                    ((ReciveImageTypeViewHolder) holder).imgTyperecive.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(context, ImagePreview.class);
//                            intent.putExtra("url", object.getEmail());
//                            context.startActivity(intent);
//
//                        }
//                    });
//
//
//
//                    break;

//                case ChatModel.SEND_PDF:
//                    Log.e("assaas", "onBindViewHolder: PDF" );
//
//
////                    Glide.with(context)
////                            .load(object.getEmail())
////                            .transition(DrawableTransitionOptions.withCrossFade())
////                            .into(((SendPDFTypeViewHolder) holder).documentView);
//                    ((SendPDFTypeViewHolder) holder).documentView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(context, ImagePreview.class);
//                            intent.putExtra("url", object.getEmail());
//                            context.startActivity(intent);
//
//                        }
//                    });
//
////
//                    ((SendPDFTypeViewHolder) holder).documentView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ object.getEmail()+".pdf");
//                            Intent target = new Intent(Intent.ACTION_VIEW);
//                            target.setDataAndType(Uri.fromFile(file),"application/pdf");
//                            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//
//                            Intent intent = Intent.createChooser(target, "Open File");
//                            try {
//                                context.startActivity(intent);
//                            } catch (ActivityNotFoundException e) {
//                                // Instruct the user to install a PDF reader here, or something
//                            }
//                        }
//                    });
////                    fromAsset(object.getEmail())
////                        .enableSwipe(true)
////                        .swipeHorizontal(false)
////                        .onPageChange((OnPageChangeListener) this)
////                        .enableAnnotationRendering(true)
////                        .onLoad((OnLoadCompleteListener) this)
////                        .load();
//
//
////                    ((SendPDFTypeViewHolder) holder).documentView.fromUrl(object.getEmail())
////                            .enableSwipe(false) // allows to block changing pages using swipe
////                            .onLoad((OnLoadCompleteListener) context) // called after document is loaded and starts to be rendered
////                            .onPageChange((OnPageChangeListener) this)
////                            .swipeHorizontal(false)
////                            .enableAntialiasing(true)
////                            .loadFromUrl();
//
//                    break;

            }

        }
    }

    @Override
    public int getItemCount() {
        Log.e(",l,l", "getItemCount: "+arrayList.size() );

        return arrayList.size();
    }

    private class RecivieTypeViewHolder extends RecyclerView.ViewHolder {
        TextView type,timeDoctorSidePt;

        public RecivieTypeViewHolder(View view) {
            super(view);

            type = view.findViewById(R.id.type);
            timeDoctorSidePt = view.findViewById(R.id.timeDoctorSidePt);
        }
    }

    private class SendTypeViewHolder extends RecyclerView.ViewHolder {
        TextView sendMsg,sendTime;
        public SendTypeViewHolder(View view) {
            super(view);
            sendMsg = itemView.findViewById(R.id.sendMsg);
            sendTime = itemView.findViewById(R.id.sendTime);
        }
    }

//    private class SendImageTypeViewHolder extends RecyclerView.ViewHolder {
//        ImageView imgTypeSend;
//        public SendImageTypeViewHolder(View view) {
//            super(view);
//            imgTypeSend = view.findViewById(R.id.imgTypeSend);
//        }
//    }
//
//    private class ReciveImageTypeViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView imgTyperecive;
//        public ReciveImageTypeViewHolder(View view) {
//            super(view);
//            imgTyperecive = view.findViewById(R.id.imgTyperecive);
//        }
//    }
//
//    private class SendPDFTypeViewHolder extends RecyclerView.ViewHolder {
//        ImageView documentView;
//        //        PDFView documentView;
//        public SendPDFTypeViewHolder(View view) {
//            super(view);
//            documentView = view.findViewById(R.id.documentView);
//        }
//    }




//    @NonNull
//    @Override
//    public ChatAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view;
//        switch (viewType) {
//
//            case ChatModel.RECEIVE_MSG:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatmsg, parent, false);
//                return new RecivieTypeViewHolder(view);
//
//            case ChatModel.SEND_MESSAGE:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_send, parent, false);
//                return new SendTypeViewHolder(view);
//        }
//    };
//
//
////            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatmsg, null);
////        return new VH(inflate);    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ChatAdapter.VH holder, int position) {
//
//        final ChatModel chatModel = arrayList.get(position);
//        sessionManager = new SessionManager(context);
//
////        Log.e(TAG, "onBindViewHolder: ", );
//        holder.type.setText(chatModel.getUser());
//        holder.timeDoctorSidePt.setText(chatModel.getTime());
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrayList.size();
//    }
//
//    public class VH extends RecyclerView.ViewHolder {
//        TextView type,timeDoctorSidePt;
//        public VH(@NonNull View itemView) {
//            super(itemView);
//            type = itemView.findViewById(R.id.type);
//            timeDoctorSidePt = itemView.findViewById(R.id.timeDoctorSidePt);
//        }
//    }
//}

}

