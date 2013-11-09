/*
 * Copyright (C) 2013 Chris Lacy
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.tweetalib.android.model;

import android.text.Html;
import android.text.Spanned;

import org.appdotnet4j.model.AdnMedia;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tweetalib.android.TwitterManager;
import org.tweetalib.android.TwitterUtil;

import java.util.ArrayList;
import java.util.Date;

import twitter4j.DirectMessage;
import twitter4j.User;

public class TwitterDirectMessage implements Comparable<TwitterDirectMessage> {

    /*
     *
	 */
    public enum MessageType {
        SENT, RECEIVED
    }

    /*
	 * 
	 */
    public TwitterDirectMessage(DirectMessage message, User otherUser) {
        mId = message.getId();
        mMessageType = message.getRecipientId() == otherUser.getId() ? MessageType.SENT
                : MessageType.RECEIVED;
        mText = message.getText();
        TwitterMediaEntity mediaEntity = TwitterMediaEntity.createMediaEntity(message);
        String descriptionMarkup = TwitterUtil.getStatusMarkup(mText, message.getMediaEntities(), message.getURLEntities());
        mTextSpanned = Html.fromHtml(descriptionMarkup + " ");
        mCreatedAt = message.getCreatedAt();
        mOtherUserId = otherUser.getId();
        mOtherUserScreenName = otherUser.getScreenName();
        mOtherUserProfileImageOriginalUrl = otherUser.getOriginalProfileImageURL();
        mOtherUserProfileImageMiniUrl = otherUser.getMiniProfileImageURL();
        mOtherUserProfileImageNormalUrl = otherUser.getProfileImageURL();
        mOtherUserProfileImageBiggerUrl = otherUser.getBiggerProfileImageURL();
        TwitterUser sender = new TwitterUser(message.getSender());
        mSenderProfileImageOriginalUrl = sender.getProfileImageUrlOriginal();
        mSenderProfileImageMiniUrl = sender.getProfileImageUrlMini();
        mSenderProfileImageNormalUrl = sender.getProfileImageUrlNormal();
        mSenderProfileImageBiggerUrl = sender.getProfileImageUrlBigger();
    }

    public TwitterDirectMessage(String jsonAsString) {

        try {
            JSONObject object = new JSONObject(jsonAsString);

            if (object.has(KEY_ID)) {
                mId = object.getLong(KEY_ID);
            }

            if (object.has(KEY_MESSAGE_TYPE)) {
                mMessageType = (MessageType)object.get(KEY_MESSAGE_TYPE);
            }

            if (object.has(KEY_TEXT)) {
                mText = object.getString(KEY_TEXT);
            }

            if (object.has(KEY_TEXT_SPANNED)) {
                mTextSpanned = (Spanned)object.get(KEY_TEXT_SPANNED);
            }

            if (object.has(KEY_CREATED_AT)) {
                long createdAt = object.getLong(KEY_CREATED_AT);
                mCreatedAt = new Date(createdAt);
            }

            if (object.has(KEY_OTHER_USER_ID)) {
                mOtherUserId = object.getLong(KEY_OTHER_USER_ID);
            }

            if (object.has(KEY_OTHER_USER_NAME)) {
                mOtherUserScreenName = object.getString(KEY_OTHER_USER_NAME);
            }

            if (object.has(KEY_PROFILE_IMAGE_OTHER_USER_ORIGINAL_URL)) {
                String url = object.getString(KEY_PROFILE_IMAGE_OTHER_USER_ORIGINAL_URL);
                if (url != null) {
                    mOtherUserProfileImageOriginalUrl = url;
                }
            }

            if (object.has(KEY_PROFILE_IMAGE_OTHER_USER_NORMAL_URL)) {
                String url = object.getString(KEY_PROFILE_IMAGE_OTHER_USER_NORMAL_URL);
                if (url != null) {
                    mOtherUserProfileImageNormalUrl = url;
                }
            }

            if (object.has(KEY_PROFILE_IMAGE_OTHER_USER_MINI_URL)) {
                String url = object.getString(KEY_PROFILE_IMAGE_OTHER_USER_MINI_URL);
                if (url != null) {
                    mOtherUserProfileImageMiniUrl = url;
                }
            }

            if (object.has(KEY_PROFILE_IMAGE_OTHER_USER_BIGGER_URL)) {
                String url = object.getString(KEY_PROFILE_IMAGE_OTHER_USER_BIGGER_URL);
                if (url != null) {
                    mOtherUserProfileImageBiggerUrl = url;
                }
            }

            if (object.has(KEY_PROFILE_IMAGE_SENDER_BIGGER_URL)) {
                String url = object.getString(KEY_PROFILE_IMAGE_SENDER_ORIGINAL_URL);
                if (url != null) {
                    mSenderProfileImageOriginalUrl = url;
                }
            }

            if (object.has(KEY_PROFILE_IMAGE_SENDER_NORMAL_URL)) {
                String url = object.getString(KEY_PROFILE_IMAGE_SENDER_NORMAL_URL);
                if (url != null) {
                    mSenderProfileImageNormalUrl = url;
                }
            }

            if (object.has(KEY_PROFILE_IMAGE_SENDER_MINI_URL)) {
                String url = object.getString(KEY_PROFILE_IMAGE_SENDER_MINI_URL);
                if (url != null) {
                    mSenderProfileImageMiniUrl = url;
                }
            }

            if (object.has(KEY_PROFILE_IMAGE_SENDER_BIGGER_URL)) {
                String url = object.getString(KEY_PROFILE_IMAGE_SENDER_BIGGER_URL);
                if (url != null) {
                    mSenderProfileImageBiggerUrl = url;
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toString() {

        JSONObject object = new JSONObject();
        try {
            object.put(KEY_ID, mId);
            object.put(KEY_MESSAGE_TYPE, mMessageType);
            object.put(KEY_TEXT, mText);
            object.put(KEY_TEXT_SPANNED, mTextSpanned);
            object.put(KEY_CREATED_AT, mCreatedAt.getTime());
            object.put(KEY_OTHER_USER_ID, mOtherUserId);
            object.put(KEY_OTHER_USER_NAME, mOtherUserScreenName);

            if (mOtherUserProfileImageOriginalUrl != null) {
                String url = mOtherUserProfileImageOriginalUrl;
                object.put(KEY_PROFILE_IMAGE_OTHER_USER_ORIGINAL_URL, url);
            }
            if (mOtherUserProfileImageMiniUrl != null) {
                String url = mOtherUserProfileImageMiniUrl;
                object.put(KEY_PROFILE_IMAGE_OTHER_USER_MINI_URL, url);
            }
            if (mOtherUserProfileImageNormalUrl != null) {
                String url = mOtherUserProfileImageNormalUrl;
                object.put(KEY_PROFILE_IMAGE_OTHER_USER_NORMAL_URL, url);
            }
            if (mOtherUserProfileImageBiggerUrl != null) {
                String url = mOtherUserProfileImageBiggerUrl;
                object.put(KEY_PROFILE_IMAGE_OTHER_USER_BIGGER_URL, url);
            }

            if (mSenderProfileImageOriginalUrl != null) {
                String url = mSenderProfileImageOriginalUrl;
                object.put(KEY_PROFILE_IMAGE_SENDER_ORIGINAL_URL, url);
            }
            if (mSenderProfileImageMiniUrl != null) {
                String url = mSenderProfileImageMiniUrl;
                object.put(KEY_PROFILE_IMAGE_SENDER_MINI_URL, url);
            }
            if (mSenderProfileImageNormalUrl != null) {
                String url = mSenderProfileImageNormalUrl;
                object.put(KEY_PROFILE_IMAGE_SENDER_NORMAL_URL, url);
            }
            if (mSenderProfileImageBiggerUrl != null) {
                String url = mSenderProfileImageBiggerUrl;
                object.put(KEY_PROFILE_IMAGE_SENDER_BIGGER_URL, url);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();

    }

    private final String KEY_ID = "mId";
    private final String KEY_MESSAGE_TYPE = "mMessageType";
    private final String KEY_TEXT = "mText";
    private final String KEY_TEXT_SPANNED = "mTextSpanned";
    private final String KEY_CREATED_AT = "mCreatedAt";
    private final String KEY_OTHER_USER_ID = "mOtherUserId";
    private final String KEY_OTHER_USER_NAME = "mOtherUserScreenName";
    private final String KEY_PROFILE_IMAGE_OTHER_USER_ORIGINAL_URL = "mOtherUserProfileImageOriginalUrl";
    private final String KEY_PROFILE_IMAGE_OTHER_USER_MINI_URL = "mOtherUserProfileImageMiniUrl";
    private final String KEY_PROFILE_IMAGE_OTHER_USER_NORMAL_URL = "mOtherUserProfileImageNormalUrl";
    private final String KEY_PROFILE_IMAGE_OTHER_USER_BIGGER_URL = "mOtherUserProfileImageBiggerUrl";
    private final String KEY_PROFILE_IMAGE_SENDER_ORIGINAL_URL = "mSenderProfileImageOriginalUrl";
    private final String KEY_PROFILE_IMAGE_SENDER_MINI_URL = "mSenderProfileImageMiniUrl";
    private final String KEY_PROFILE_IMAGE_SENDER_NORMAL_URL = "mSenderProfileImageNormalUrl";
    private final String KEY_PROFILE_IMAGE_SENDER_BIGGER_URL = "mSenderProfileImageBiggerUrl";


    /*
	 * 
	 */
    public long getId() {
        return mId;
    }

    public MessageType getMessageType() {
        return mMessageType;
    }

    public String getText() {
        return mText;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public long getOtherUserId() {
        return mOtherUserId;
    }

    public String getOtherUserScreenName() {
        return mOtherUserScreenName;
    }

    public String getOtherUserProfileImageUrl(TwitterManager.ProfileImageSize size) {

        switch (size) {
            case MINI:
                return mOtherUserProfileImageMiniUrl;
            case NORMAL:
                return mOtherUserProfileImageNormalUrl;
            case BIGGER:
                return mOtherUserProfileImageBiggerUrl;
            case ORIGINAL:
                return mOtherUserProfileImageOriginalUrl;
        }
        return "";
    }

    public String getSenderProfileImageUrl(TwitterManager.ProfileImageSize size) {

        switch (size) {
            case MINI:
                return mSenderProfileImageMiniUrl;
            case NORMAL:
                return mSenderProfileImageNormalUrl;
            case BIGGER:
                return mSenderProfileImageBiggerUrl;
            case ORIGINAL:
                return mSenderProfileImageOriginalUrl;
        }
        return "";
    }

    /*
	 *
	 */
    private long mId;
    private MessageType mMessageType;
    private String mText;
    public Spanned mTextSpanned;
    private Date mCreatedAt;
    private long mOtherUserId;
    private String mOtherUserScreenName;
    private String mOtherUserProfileImageOriginalUrl;
    private String mOtherUserProfileImageMiniUrl;
    private String mOtherUserProfileImageNormalUrl;
    private String mOtherUserProfileImageBiggerUrl;
    private String mSenderProfileImageOriginalUrl;
    private String mSenderProfileImageMiniUrl;
    private String mSenderProfileImageNormalUrl;
    private String mSenderProfileImageBiggerUrl;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(TwitterDirectMessage another) {
        // TODO: Change to use messageID
        if (mCreatedAt.before(another.mCreatedAt)) {
            return 1;
        }
        return -1;
    }
}
