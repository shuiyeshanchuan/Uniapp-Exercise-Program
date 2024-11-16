<template>
  <div>
    <el-upload
        :action="uploadUrl"
        :list-type="listType"
        :on-preview="handlePreview"
        :file-list="fileList"
        :limit="limit"
        :accept="accept"
        :headers="uploadHeaders"
        :on-success="handleFileSuccess"
        :on-exceed="handleExceed"
        :on-remove="handleRemove">
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">{{ tip }}</div>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible" append-to-body>
      <img v-if="type==='image'||type==='imageCar'" width="100%" :src="file.url" alt="">
      <video v-if="type==='video'" width="100%" :src="file.url" controls></video>
      <audio v-if="type==='audio'" width="100%" :src="file.url" controls></audio>
    </el-dialog>
  </div>
</template>

<script>
import utils from "@/utils/utils";

export default {
  name: "Upload",
  props: {
    type: {
      type: String,
      default: () => {
        return ""
      }
    },
    files: {
      type: String,
      default: () => {
        return ""
      }
    },
    tip: {
      type: String,
      default: () => {
        return ""
      }
    },
    limit: {
      type: Number,
      default: () => {
        return 100
      }
    }
  },
  data() {
    return {
      uploadHeaders: {
        token: utils.getToken()
      },
      uploadUrl: "",
      fileList: [],
      accept: "",
      listType: "picture-card",
      dialogVisible: false,
      file: ""
    }
  },
  created() {
    this.uploadUrl = process.env.VUE_APP_SERVER + "/file/upload/img"
    this.load()
    console.log(this.uploadUrl)
  },
  methods: {
    load() {
      if (this.files) {
        this.fileList = JSON.parse(this.files)
      }
      console.log(this.type)
      switch (this.type) {
        case "imageCar":
          this.listType = "picture-card"
          this.accept = "image/*"
          break;
        case "image":
          this.listType = "picture"
          this.accept = "image/*"
          break;
        case "video":
          this.accept = "video/*"
          this.listType = "text"
          break;
        case "audio":
          this.accept = "audio/*"
          this.listType = "text"
          break;
        case "file":
          this.listType = "text"
          break;
      }
    },
    setFiles() {
      let files = JSON.stringify(this.fileList.map((item) => {
        return {
          name: item.name,
          url: item.url
        }
      }));
      this.$emit("setFiles", files);
    },
    handleFileSuccess(response, file, fileList) {
      console.log(response, file, fileList)
      this.fileList.push({
        name: response.data.alt,
        url: response.data.url
      });
      this.setFiles()
    },
    handleRemove(file, fileList) {
      this.fileList = fileList;
      this.setFiles()
    },
    handlePreview(file) {
      this.file = file;
      if (this.type === "file") {
        this.downloadFile();
        return;
      }
      this.dialogVisible = true;
    },
    handleExceed() {
      this.$message.warning(`最多只允许上传${this.limit}张图片`);
    },
    downloadFile() {
      const link = document.createElement('a');
      link.style.display = 'none';
      document.body.appendChild(link);
      link.href = this.file.url;
      console.log(this.file)
      link.setAttribute('download', this.file.name); // 你可以自定义下载时的文件名
      link.click();
      link.remove();
    }
  }
}
</script>


<style>
</style>
