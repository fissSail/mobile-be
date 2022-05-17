<template>
  <div>
    <el-upload

      ref="upload"
      action="#"
      multiple
      accept="image/png,image/gif,image/jpg,image/jpeg"
      list-type="picture-card"
      :auto-upload="false"
      :on-exceed="handleExceed"
      :before-upload="handleBeforeUpload"
      :on-preview="handlePictureCardPreview"
      :on-change="handleChange"
      :on-remove="handleRemove"
    >
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="" />
    </el-dialog>
  </div>
</template>

<script>
// import * as imageConversion from "image-conversion";

export default {
  data() {
    return {
      dialogImageUrl: "",
      dialogVisible: false,
      imgList: []
    };
  },
  methods: {
    // 上传文件之前的钩子
    handleBeforeUpload(file) {
      if (
        !(
          file.type === "image/png" ||
          file.type === "image/gif" ||
          file.type === "image/jpg" ||
          file.type === "image/jpeg"
        )
      ) {
        this.$notify.warning({
          title: "警告",
          message:
            "请上传格式为image/png, image/gif, image/jpg, image/jpeg的图片"
        });
      }
      let size = file.size / 1024 / 1024 / 5;
      if (size > 5) {
        this.$notify.warning({
          title: "警告",
          message: "图片大小必须小于5M"
        });
      }
    },
    // 文件状态改变时的钩子，添加文件、上传成功和上传失败的钩子
    handleChange(file, fileList) {
      this.imgList = [];
      fileList.forEach(item => {
        console.log("fileList",fileList)
       //  this.imgList.push(item.url);
        this.blobToBase64(item.raw).then(res => {
          this.imgList.push(res);
        });
      });
      this.$emit("imgList", this.imgList);
    },
    // 文件超出个数限制时的钩子
    handleExceed(files, fileList) {},
    // 文件列表移除文件时的钩子
    handleRemove(file, fileList) {
      this.blobToBase64(file.raw).then(res => {
        this.imgList.forEach((item, index) => {
          if (item == res) {
            this.imgList.splice(index, 1);
          }
        });
        this.$emit("imgList", this.imgList);
      });
    },
    // 点击文件列表中已上传的文件时的钩子
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    compress(file){
      return new Promise((resolve, reject) => {
        // console.log('压缩前', file) // 压缩到400KB,大于400KB的图片都会进行压缩，小于则不会
        imageConversion.compressAccurately(file, 400).then(res => {  
          res = new File([res], file.name, { type: res.type, lastModified: Date.now() })
          resolve(res)
        })
      })
    },
    blobToBase64(blob) {
      return new Promise((resolve, reject) => {
        const fileReader = new FileReader();
        fileReader.onload = e => {
          resolve(e.target.result);
        };
        // readAsDataURL
        fileReader.readAsDataURL(blob);
        fileReader.onerror = () => {
          reject(new Error("文件流异常"));
        };
      });
    }
  }
};
</script>

<style></style>
