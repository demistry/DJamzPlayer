package com.djamzplayer.android.utils;

import android.content.IntentFilter;

/**
 * Created by ILENWABOR DAVID on 31/12/2017.
 */

public class ReceiverFilter {

    public static IntentFilter getNotificationFilter(){
        IntentFilter filter = new IntentFilter(ContractClass.PLAY);
        filter.addAction(ContractClass.PAUSE);
        filter.addAction(ContractClass.NEXT);
        filter.addAction(ContractClass.PREVIOUS);
        filter.addAction(ContractClass.CANCEL);
        return filter;
    }
}
