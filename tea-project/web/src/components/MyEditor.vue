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
            server: process.env.VUE_APP_SERVER + "/file/upload/img",//上传接口地址
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
            server: process.env.VUE_APP_SERVER + "/file/upload/video",//上传接口地址
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
  created() {
  },
  methods: {
    onCreated(editor) {
      this.editor = Object.seal(editor);
    },
    onChange(editor) {
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
