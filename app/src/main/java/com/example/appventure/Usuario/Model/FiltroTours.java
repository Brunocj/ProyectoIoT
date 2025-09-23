package com.example.appventure.Usuario.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class FiltroTours  implements Parcelable {
    public Long fechaUtc;                // null si no eligió
    public float precioMin = 50f;
    public float precioMax = 500f;
    public List<String> ubicaciones = new ArrayList<>();
    public boolean catCultural, catNaturaleza, catSenderismo;
    public Integer minRating;            // 5,4,3,2 o null

    public FiltroTours() {}

    protected FiltroTours(Parcel in) {
        fechaUtc = (in.readByte()==0) ? null : in.readLong();
        precioMin = in.readFloat();
        precioMax = in.readFloat();
        ubicaciones = in.createStringArrayList();
        catCultural = in.readByte()!=0;
        catNaturaleza = in.readByte()!=0;
        catSenderismo = in.readByte()!=0;
        minRating = (in.readByte()==0) ? null : in.readInt();
    }

    public static final Creator<FiltroTours> CREATOR = new Creator<FiltroTours>() {
        public FiltroTours createFromParcel(Parcel in) { return new FiltroTours(in); }
        public FiltroTours[] newArray(int size) { return new FiltroTours[size]; }
    };

    public int describeContents() { return 0; }
    public void writeToParcel(Parcel dest, int flags) {
        if (fechaUtc==null) dest.writeByte((byte)0); else { dest.writeByte((byte)1); dest.writeLong(fechaUtc); }
        dest.writeFloat(precioMin);
        dest.writeFloat(precioMax);
        dest.writeStringList(ubicaciones);
        dest.writeByte((byte)(catCultural?1:0));
        dest.writeByte((byte)(catNaturaleza?1:0));
        dest.writeByte((byte)(catSenderismo?1:0));
        if (minRating==null) dest.writeByte((byte)0); else { dest.writeByte((byte)1); dest.writeInt(minRating); }
    }

}
