<template>
  <div class="storage-ts-container col mb-5">
    <div class="storage-ts-output-cont">
      <div style="display: flex; justify-content: space-between">
        <div style="display: flex">
          <div style="margin-right: 1rem">
            <h4>간단번역</h4>
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
        <output name="result">
          <div v-html="source"></div>
        </output>
      </div>
      <div class="storage-img-box">
        <output name="result">
          <div v-html="result"></div>
        </output>
      </div>
      <div style="display: flex">
        <button @click="download('input')" class="storage-ff1-btn mt-4">
          원문 다운로드
        </button>
        <button @click="download('output')" class="storage-ff2-btn mt-4">
          번역문 다운로드
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
  name: "PapagoStorageCard",
  props: {
    translation_no: Number,
    email: String,
    input: String,
    output: String,
    trans_date: String,
  },
  data() {
    return {};
  },
  methods: {
    async remove() {
      let str = "/api/papago/remove";
      let form = new FormData();
      form.append("email", this.email);
      form.append("translation_no", this.translation_no);

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
    download(kind) {
      console.log(this.translation_no + ", " + this.email);
      let str =
        "/api/papago/download/" +
        this.email +
        "/" +
        this.translation_no +
        "/" +
        kind;

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
    source() {
      return this.input.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    result() {
      return this.output.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
  },
};
</script>

<style>
</style>