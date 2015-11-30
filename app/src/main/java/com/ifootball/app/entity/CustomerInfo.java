package com.ifootball.app.entity;

import java.io.Serializable;

public class CustomerInfo implements Serializable {
	private static final long serialVersionUID = -5328321004877325242L;
	private int SysNo;
	private String NickName;
	private String Email;
	private String CellPhone;
	private String Password;
	private String Avatar;
	private String QQOpenID;
	private String WXOpenID;
	// private Date RegTime;
	private String RegTimeStr;
	// private Date LastLoginTime;
	private String LastLoginIP;
	private int Gender;
	private int Status;
	private CustomerProfile ProfileInfo;

	public int getSysNo() {
		return SysNo;
	}

	public void setSysNo(int sysNo) {
		SysNo = sysNo;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getCellPhone() {
		return CellPhone;
	}

	public void setCellPhone(String cellPhone) {
		CellPhone = cellPhone;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getAvatar() {
		return Avatar;
	}

	public void setAvatar(String avatar) {
		Avatar = avatar;
	}

	public String getQQOpenID() {
		return QQOpenID;
	}

	public void setQQOpenID(String qQOpenID) {
		QQOpenID = qQOpenID;
	}

	public String getWXOpenID() {
		return WXOpenID;
	}

	public void setWXOpenID(String wXOpenID) {
		WXOpenID = wXOpenID;
	}

	//
	// public Date getRegTime() {
	// return RegTime;
	// }
	//
	// public void setRegTime(Date regTime) {
	// RegTime = regTime;
	// }

	public String getRegTimeStr() {
		return RegTimeStr;
	}

	public void setRegTimeStr(String regTimeStr) {
		RegTimeStr = regTimeStr;
	}

	//
	// public Date getLastLoginTime() {
	// return LastLoginTime;
	// }
	//
	// public void setLastLoginTime(Date lastLoginTime) {
	// LastLoginTime = lastLoginTime;
	// }

	public String getLastLoginIP() {
		return LastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		LastLoginIP = lastLoginIP;
	}

	public int getGender() {
		return Gender;
	}

	public void setGender(int gender) {
		Gender = gender;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public CustomerProfile getProfileInfo() {
		return ProfileInfo;
	}

	public void setProfileInfo(CustomerProfile profileInfo) {
		ProfileInfo = profileInfo;
	}

	public class CustomerProfile {
		private int CustomerSysNo;
		private int Height;
		private int Weight;
		private int Birthday;
		private String YearsExp;
		private String Position;
		private String Signatures;

		public int getCustomerSysNo() {
			return CustomerSysNo;
		}

		public void setCustomerSysNo(int customerSysNo) {
			CustomerSysNo = customerSysNo;
		}

		public int getHeight() {
			return Height;
		}

		public void setHeight(int height) {
			Height = height;
		}

		public int getWeight() {
			return Weight;
		}

		public void setWeight(int weight) {
			Weight = weight;
		}

		public int getBirthday() {
			return Birthday;
		}

		public void setBirthday(int birthday) {
			Birthday = birthday;
		}

		public String getBrithdayStr() {
			if (String.valueOf(Birthday).length() == 8) {
				return String.format("%s-%s-%s", String.valueOf(Birthday)
						.substring(0, 4),
						String.valueOf(Birthday).substring(4, 6), String
								.valueOf(Birthday).substring(6, 8));
			} else {
				return "";
			}
		}

		public void setBrithdayStr(String brithdayStr) {
			if (brithdayStr.replaceAll("-", "").length() == 8) {
				Birthday = Integer.parseInt(brithdayStr.replaceAll("-", ""));
			}
		}

		public int GetBrithdayOfYear() {
			if (String.valueOf(Birthday).length() == 8) {
				return Integer.parseInt(String.valueOf(Birthday)
						.substring(0, 4));
			} else {
				return 1980;
			}
		}

		public int GetBrithdayOfMonth() {
			if (String.valueOf(Birthday).length() == 8) {
				return Integer.parseInt(String.valueOf(Birthday)
						.substring(4, 6));
			} else {
				return 1;
			}
		}

		public int GetBrithdayOfDay() {
			if (String.valueOf(Birthday).length() == 8) {
				return Integer.parseInt(String.valueOf(Birthday)
						.substring(6, 8));
			} else {
				return 1;
			}
		}

		public String getYearsExp() {
			return YearsExp;
		}

		public void setYearsExp(String yearsExp) {
			YearsExp = yearsExp;
		}

		public String getPosition() {
			return Position;
		}

		public void setPosition(String position) {
			Position = position;
		}

		public String getSignatures() {
			return Signatures;
		}

		public void setSignatures(String signatures) {
			Signatures = signatures;
		}

	}
}
