package com.taein.thignsflowtest.github.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "user")
public class User implements Serializable {
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "login_id")
    private String login;

    @ColumnInfo(name = "avatar_url")
    private String avatar_url;
}
