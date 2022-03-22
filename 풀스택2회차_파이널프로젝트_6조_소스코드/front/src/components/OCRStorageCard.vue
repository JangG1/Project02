<template>
  <div class="storage-ts-container mb-5">
    <div class="storage-ts-output-cont">
      <div style="display: flex; justify-content: space-between">
        <div style="display: flex">
          <div style="margin-right: 1rem">
            <h4>문서변환</h4>
          </div>
          <div>
            <h4>{{ trans_date }}</h4>
          </div>
        </div>
        <div class="remove-btn">
          <button @click="remove()">삭제</button>
        </div>
      </div>
      <!--여기까지 복사해서 붙여넣기-->
      <div class="storage-img-box">
        <img :src="input" alt="" class="img-fit" v-show="showInput" />
        <output name="result" v-show="showOutput">
          <div v-html="content"></div>
        </output>
      </div>
      <div style="display: flex">
        <button @click="changeShow()" class="storage-ff1-btn mt-4">
          {{ text }}
        </button>
        <button @click="download()" class="storage-ff2-btn mt-4">
          다운로드
        </button>
      </div>
    </div>
    <!--storage-ts-output-cont-end-->
  </div>
  <!--storage-ts-container-end-->
</template>

<script>
import axios from "axios";
export default {
  name: "StorageCard",
  props: {
    media_no: Number,
    email: String,
    kind: String,
    input: String,
    output: String,
    trans_date: String,
  },
  data() {
    return {
      showInput: true,
      showOutput: false,
      btnText: "텍스트 보기",
    };
  },
  methods: {
    changeText(check) {
      if (check) this.btnText = "텍스트 보기";
      else this.btnText = "사진 보기";
    },
    changeShow() {
      if (this.showInput) {
        this.showInput = false;
      } else {
        this.showInput = true;
      }
      if (this.showOutput) {
        this.showOutput = false;
      } else {
        this.showOutput = true;
      }
      this.changeText(this.showInput);
    },
    async remove() {
      let str = "/api/ocr/remove";
      let form = new FormData();
      form.append("email", this.email);
      form.append("media_no", this.media_no);
      this.$store.dispatch("setLoading", true);
      await axios
        .post(str, form)
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
      this.$store.dispatch("setLoading", false);
      this.$router.go("/storage");
    },
    download() {
      let str = "/api/ocr/download/" + this.email + "/" + this.media_no + "/";
      if (this.showInput) str += "input";
      else str += "output";

      axios
        .get(str, {
          responseType: "blob",
        })
        .then((res) => {
          const name = res.headers["content-disposition"]
            .split("fileName=")[1]
            .replace(/"/g, "");
          const url = window.URL.createObjectURL(new Blob([res.data]));
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", name); //or any other extension document.body.appendChild(link); link.click();
          document.body.appendChild(link);
          link.click();
          link.remove();
          console.log(res);
          console.log("다운로드 성공");
        })
        .catch((err) => {
          err;
          console.log("다운로드 실패");
        });
    },
  },
  computed: {
    content() {
      return this.output.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    text() {
      return this.btnText;
    },
  },
};
</script>

<style>
</style>