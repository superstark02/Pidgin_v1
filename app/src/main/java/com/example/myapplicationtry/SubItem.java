package com.example.myapplicationtry;

    public class SubItem {
        private int subItemImage;
        private String subItemTitle;
        private int subItemfees;

        public SubItem(String subItemTitle, int subItemfees) {
            this.subItemTitle = subItemTitle;
            this.subItemfees = subItemfees;
        }

        public int getSubItemImage() {
            return subItemImage;
        }

        public void setSubItemImage(int subItemImage) {
            this.subItemImage = subItemImage;
        }

        public String getSubItemTitle() {
            return subItemTitle;
        }

        public void setSubItemTitle(String subItemTitle) {
            this.subItemTitle = subItemTitle;
        }

        public int getSubItemfees() {
            return subItemfees;
        }

        public void setSubItemfees(int subItemfees) {
            this.subItemfees = subItemfees;
        }
    }

