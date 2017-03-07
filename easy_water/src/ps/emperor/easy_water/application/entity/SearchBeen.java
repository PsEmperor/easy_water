package ps.emperor.easy_water.application.entity;

import java.io.Serializable;
import java.util.List;

public class SearchBeen {
	

    private List<IrriListBean> irriList;

    public List<IrriListBean> getIrriList() {
        return irriList;
    }

    public void setIrriList(List<IrriListBean> irriList) {
        this.irriList = irriList;
    }

    public static class IrriListBean implements Serializable{
        /**
         * IrriEquTpye : 1   						设备类型
         * ValueControlNum : 2   					阀控器数量
         * FirstDerviceID : SB001001  				阀控器id
         * IrriMothed : 1   			   			轮灌类型
         * Area : 100.0		 						面积
         * IrriUnitName : 灌溉单元						灌溉单元名称
         * Latitude : 120.12346						纬度
         * Longitude : 110.12346					经度
         * SuperEqu : 配水设备5						上级单位
         * AuthName : 河北省石家庄市长安区第一连管理单位1		授权单位
         * AuthID : 1								授权ID
         * ClassID : 1								// 水源类型 0是渠道，1，机井，2，自压		7222				
         */

        private String IrriEquTpye;
        private int ValueControlNum;
        private String FirstDerviceID;
        private int IrriMothed;
        private double Area;
        private String IrriUnitName;
        private double Latitude;
        private double Longitude;
        private String SuperEqu;
        private String AuthName;
        private int AuthID;
        private int ClassID;

        public String getIrriEquTpye() {
            return IrriEquTpye;
        }

        public void setIrriEquTpye(String IrriEquTpye) {
            this.IrriEquTpye = IrriEquTpye;
        }

        public int getValueControlNum() {
            return ValueControlNum;
        }

        public void setValueControlNum(int ValueControlNum) {
            this.ValueControlNum = ValueControlNum;
        }

        public String getFirstDerviceID() {
            return FirstDerviceID;
        }

        public void setFirstDerviceID(String FirstDerviceID) {
            this.FirstDerviceID = FirstDerviceID;
        }

        public int getIrriMothed() {
            return IrriMothed;
        }

        public void setIrriMothed(int IrriMothed) {
            this.IrriMothed = IrriMothed;
        }

        public double getArea() {
            return Area;
        }

        public void setArea(double Area) {
            this.Area = Area;
        }

        public String getIrriUnitName() {
            return IrriUnitName;
        }

        public void setIrriUnitName(String IrriUnitName) {
            this.IrriUnitName = IrriUnitName;
        }

        public double getLatitude() {
            return Latitude;
        }

        public void setLatitude(double Latitude) {
            this.Latitude = Latitude;
        }

        public double getLongitude() {
            return Longitude;
        }

        public void setLongitude(double Longitude) {
            this.Longitude = Longitude;
        }

        public String getSuperEqu() {
            return SuperEqu;
        }

        public void setSuperEqu(String SuperEqu) {
            this.SuperEqu = SuperEqu;
        }

        public String getAuthName() {
            return AuthName;
        }

        public void setAuthName(String AuthName) {
            this.AuthName = AuthName;
        }

        public int getAuthID() {
            return AuthID;
        }

        public void setAuthID(int AuthID) {
            this.AuthID = AuthID;
        }

        public int getClassID() {
            return ClassID;
        }

        public void setClassID(int ClassID) {
            this.ClassID = ClassID;
        }
    }

}
