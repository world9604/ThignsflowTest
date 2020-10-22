package com.taein.thignsflowtest.github.data.vo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    @SerializedName("id")
    private int id;

    @SerializedName("login")
    private String loginId;

    @SerializedName("avatar_url")
    private String avatar_url;
}
