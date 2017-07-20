package com.retrofit.retrofitdemo.bean;

import java.util.List;

public class CmsEntity extends BaseBean {
	public CmsVo obj;

	@Override
	public String toString() {
		return "CmsEntity{" +
				"obj=" + obj +
				'}';
	}

	public class CmsVo {

		public List<ImageVo> bannerList;// 首页轮播图

		public List<ImageVo> activeList;// 返回活动数组
		public	List<ArticleTitleVo> articleTitleList;// 返回公告信息

		@Override
		public String toString() {
			return "CmsVo{" +
					"bannerList=" + bannerList +
					", activeList=" + activeList +
					", articleTitleList=" + articleTitleList +
					'}';
		}
	}

	public class ImageVo {

		public String imageAlt;// 图片标题

		public String subTitle;// 副标题

		public String imageSrc;// 图片链接地址

		public int target;// 目标链接打开方式(0:不新开页面,1:新开页面)

		public String targetHref;// 目标链接

		public int activeType;// 活动类型(0:最新活动; 1:邀请活动; 2:已过期;3,临时活动)
        public String activityStartTime;// 活动开始时间
        public String activityEndTime;//活动结束时间

		@Override
		public String toString() {
			return "ImageVo{" +
					"imageAlt='" + imageAlt + '\'' +
					", subTitle='" + subTitle + '\'' +
					", imageSrc='" + imageSrc + '\'' +
					", target=" + target +
					", targetHref='" + targetHref + '\'' +
					", activeType=" + activeType +
					", activityStartTime='" + activityStartTime + '\'' +
					", activityEndTime='" + activityEndTime + '\'' +
					'}';
		}
	}

	public class ArticleTitleVo {

		public String articleId;// 文章id

		public String title;// 主标题

		public String href;// 外部链接

		public String isNew;// 是否加新（1:加新，0:不加新）

		public String description;// 文章摘要

		public String createDate;// 文章发布时间

		@Override
		public String toString() {
			return "ArticleTitleVo{" +
					"articleId='" + articleId + '\'' +
					", title='" + title + '\'' +
					", href='" + href + '\'' +
					", isNew='" + isNew + '\'' +
					", description='" + description + '\'' +
					", createDate='" + createDate + '\'' +
					'}';
		}
	}
}
