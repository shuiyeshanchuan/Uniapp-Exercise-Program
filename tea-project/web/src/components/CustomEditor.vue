<template>
  <div>
    <div class="nav" style="border: 1px solid #ccc; margin-top: 10px;color: #000">
      <!-- 工具栏 -->
      <Toolbar
          style="border-bottom: 1px solid #ccc"
          :editor="editor"
          :defaultConfig="toolbarConfig"
      />
      <!-- 编辑器 -->
      <Editor
          style="height: 400px; overflow-y: hidden"
          :defaultConfig="editorConfig"
          v-model="html"
          @onChange="onChange"
          @onCreated="onCreated"
      />
    </div>
  </div>
</template>
<script>
import {Editor, Toolbar} from "@wangeditor/editor-for-vue";
import utils from "@/utils/utils";

export default {
  props: {
    content: {
      type: String,
      default: "",
    }
  },
  name: "MyEditor",
  components: {Editor, Toolbar},
  data() {
    return {
      editor: null,
      html: this.content,
      toolbarConfig: {
        // toolbarKeys: [ /* 显示哪些菜单，如何排序、分组 */ ],
        // excludeKeys: [ /* 隐藏哪些菜单 */ ],
      },
      editorConfig: {
        placeholder: "请输入内容...",
        // autoFocus: false,
        MENU_CONF: {
          uploadImage: {
            server: this.$httpUploadFile + "",//上传接口地址
            headers: {
              token: utils.getToken()
            },
            fieldName: 'file',//上传文件名
            customInsert(res, insertFn) {
              insertFn(res.data.url, res.data.alt, res.data.url)
            },
          },
          uploadVideo: {
            // 单个文件的最大体积限制，默认为 10M
            maxFileSize: 100 * 1024 * 1024, // 10M
            server: this.$httpUploadFile + "",//上传接口地址
            // server: process.env.VUE_APP_SERVER+"/wangeditorUploads",//上传接口地址
            headers: {
              token: utils.getToken()
            },
            fieldName: 'file',//上传文件名
            customInsert(res, insertFn) {
              insertFn(res.data.url, res.data.alt, res.data.url)
            },
          }
        }
      },
    };
  },
  //监听数值
  watch: {
    content(newValue) {
      console.log("newValue:")

      console.log(newValue)
      this.html = newValue;
    }
  },
  created() {
  },
  methods: {
    onCreated(editor) {
      this.editor = Object.seal(editor);
    },
    onChange(editor) {
      console.log("onChange", editor.getHtml())
      this.$emit("content-change", editor.getHtml())
    },
  },
  mounted() {
  },
  beforeDestroy() {
    const editor = this.editor;
    if (editor == null) return;
    editor.destroy();
  },
};
</script>
<style src="@wangeditor/editor/dist/css/style.css"></style>
<style>
.nav .title {
  margin-top: 10px;
  color: #000 !important;
}
</style>
<!--<el-dialog title="信息" :visible.sync="dialogFormVisible" width="81%"  :before-close="cancel">-->
<!--<CustomEditor :content="form.content" @content-change="contentChange"></CustomEditor>-->
<!--cancel(){-->
<!--this.dialogFormVisible = false-->
<!--console.log("执行")-->
<!--location.reload();-->
<!--},-->
<!--contentChange(e) {-->
<!--console.log(e)-->
<!--this.form.content=e-->
<!--},-->

<!--保存需要添加          location.reload();-->
