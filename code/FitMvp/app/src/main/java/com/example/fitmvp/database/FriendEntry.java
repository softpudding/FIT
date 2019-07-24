package com.example.fitmvp.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/*
 * 好友列表记录
 */
@Table(name = "friends", id = "_id")
public class FriendEntry extends Model {

    @Column(name = "Uid")
    public Long uid;

    // username 即为 phone
    @Column(name = "Username")
    public String username;

    @Column(name = "AppKey")
    public String appKey;

    @Column(name = "Avatar")
    public String avatar;

    @Column(name = "DisplayName")
    public String displayName;

    @Column(name = "Letter")
    public String letter;

    @Column(name = "NickName")
    public String nickName;

    @Column(name = "NoteName")
    public String noteName;

    @Column(name = "User")
    public UserEntry user;

    @Column(name = "gender")
    public String gender;

    @Column(name = "birthday")
    public String birthday;

    public FriendEntry() {
        super();
    }

    public FriendEntry(Long uid, String username, String noteName, String nickName, String appKey, String avatar, String displayName, String letter,
                       String gender, String birthday,
                       UserEntry user) {
        super();
        this.uid = uid;
        this.username = username;
        this.appKey = appKey;
        this.avatar = avatar;
        this.displayName = displayName;
        this.letter = letter;
        this.user = user;
        this.noteName = noteName;
        this.nickName = nickName;
        this.gender = gender;
        this.birthday = birthday;
    }

    public static FriendEntry getFriend(UserEntry user, String username, String appKey) {
        return new Select().from(FriendEntry.class)
                .where("Username = ?", username)
                .where("AppKey = ?", appKey)
                .where("User = ?", user.getId())
                .executeSingle();
    }

    public static FriendEntry getFriend(long id) {
        return new Select().from(FriendEntry.class).where("_id = ?", id).executeSingle();
    }

    public void setNickName(String name){
        nickName = name;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public void setBirthday(String birthday){
        this.birthday = birthday;
    }
    public void setAvatar(String avatar){
        this.avatar = avatar;
    }
}
