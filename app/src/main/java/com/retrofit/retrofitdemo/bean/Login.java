package com.retrofit.retrofitdemo.bean;

import java.io.Serializable;

/**
 * 登录bean
 * 
 * @author Administrator
 *
 */
public class Login extends BaseBean implements Serializable{
	public UserInfo obj;// 返回信息的实体类

	@Override
	public String toString() {
		return "Login{" +
				"obj=" + obj +
				'}';
	}

	public class UserInfo implements Serializable{
		public int num;// 如果登录失败，会记录次数，如果次数大于等于4，就要输入验证码
		public int errorCount;
		public int LoanUser;// 是否是借款用户 0：否 1：是
		public String cardNo; // 身份证号
//		public int dealPwd; // 是否设置交易密码 0：否 1：是
		public int firstInvest; // 是否第一次投资 0：否 1：是
		public int firstRecharge; // 是否第一次充值 0：否 1：是
		public String mobilePhone; // 用户手机号
		public String realName; // 真实姓名
		public int realNameOrNot; // 是否实名认证 0：否 1：是
		public int superUser; // 是否为超级用户0:普通用户，1：超级用户，2：中间用户
		public String userId; // 用户ID
		public int userType; // 用户类型 １、普通用户，２、企业用户，３、系统账户
		public String token; 
		public Cardata bankCard; // 绑定的银行卡信息
		public int userAccountType;//用户帐户类型1, "个人基本户"2, "保证金户"3, "准备金"4, "存钱罐"5, "企业基本户"6, " 企业存钱罐"
		public int denyWithdraw;// 是否禁止提现(0:不禁止；1：禁止)
		public String denyWithdrawMsg;// 禁止提现返回文案(如:春节期间,暂停充值提现)
		public int denyRecharge;// 是否禁止充值(0:不禁止；1：禁止)
		public String denyRechargeMsg;// 禁止充值返回文案(如:春节期间,暂停充值提现)
		public int isSetPayPwd;// 是否设置了新浪的交易密码 0：否 1：是
		public int isWithholdAuthorize;//  1已开通 0未开通
		public boolean hasSign;//本地设置  非接口返回

		@Override
		public String toString() {
			return "UserInfo{" +
					"num=" + num +
					", errorCount=" + errorCount +
					", LoanUser=" + LoanUser +
					", cardNo='" + cardNo + '\'' +
					", firstInvest=" + firstInvest +
					", firstRecharge=" + firstRecharge +
					", mobilePhone='" + mobilePhone + '\'' +
					", realName='" + realName + '\'' +
					", realNameOrNot=" + realNameOrNot +
					", superUser=" + superUser +
					", userId='" + userId + '\'' +
					", userType=" + userType +
					", token='" + token + '\'' +
					", bankCard=" + bankCard +
					", userAccountType=" + userAccountType +
					", isSetPayPwd=" + isSetPayPwd +
					'}';
		}
	}
	
	public class Cardata implements Serializable{
		/** "ICBC" 银行编码 */
		public String bankCode;
		/** "中国工商银行" */
		public String bankName;
		/** "36214" 新浪银行卡号 */
		public String cardId;
		/** "6222000200103824280" 银行卡号 */
		public String cardNo;
		/** 961 */
		public String id;
		/** 1 是否快捷卡 */
		public String isDefault;
		/** "15910954033" 绑定的手机号 */
		public String phoneNo;
		/** "吴桂仙" 持卡人 */
		public String realName;
		/** 今日已充金额 */
		public double hasRechargeToday;
		/** double 单日限额 */
		public double dailyLimit;
		/** double 单日单笔限额 */
		public double singleDayLimit;
		/** double 单笔最低支付限额 */
		public double singleMinPayLimit;
		/** double 首次绑卡充值限额 */
		public double firstBindingPay;
		/** double 提现单日限额 */
		public double withdrawDailyLimit;
		/** double 提现单笔限额 */
		public double singleWithdrawLimit;

		@Override
		public String toString() {
			return "Cardata [bankCode=" + bankCode + ", bankName=" + bankName + ", cardId=" + cardId + ", cardNo="
					+ cardNo + ", id=" + id + ", isDefault=" + isDefault + ", phoneNo=" + phoneNo + ", realName="
					+ realName + ", hasRechargeToday=" + hasRechargeToday + "]";
		}

	}


}
