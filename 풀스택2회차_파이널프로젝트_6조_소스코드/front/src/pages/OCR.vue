<template>
  <div class="field">
    <div class="top-container px-5 py-5">
      <div class="profile-logo">
        <div>
          <h2>문서변환</h2>
        </div>
        <div style="margin-left: 41%">
          <h2 @click="$router.push('/')">PAGO BOOKS</h2>
        </div>
      </div>
      <!--profile-logo-end-->
    </div>
    <!--top-container-end-->

    <div class="ocr-bottom-container px-5 pt-5 pb-2">
      <div class="ocr-ts-container">
        <div class="ocr-ts-input-cont">
          <div class="ocr-img-box">
            <img :src="image" class="ocr-img-fit" />
          </div>
          <div class="ocr-cf-btn mt-4">
            <label for="chooseFile">파일 가져오기</label>
            <form method="post" enctype="multipart/form-data">
              <input
                ref="image"
                @change="uploadImg()"
                type="file"
                id="chooseFile"
                name="chooseFile"
                accept="image/*"
                style="display: none"
              />
            </form>
          </div>
        </div>
        <!--ts-input-cont-end-->

        <div class="ocr-output-cont">
          <div class="ocr-ts-box">
            <form action="#">
              <output name="result" for="text">
                <div v-html="content"></div>
              </output>
            </form>
          </div>
          <div style="display: flex">
            <div class="ocr-ts-lg-ch mt-4">
              <select name="ts-lg" id="ts-lg" v-model="from_language">
                <option value="ko">한국어</option>
                <option value="en">영어</option>
                <option value="ja">일본어</option>
                <option value="zh-CN">중국어</option>
                <option value="de">독일어</option>
                <option value="es">스페인어</option>
              </select>
            </div>
            <div class="ocr-ts-lg-ch mt-4">
              <select name="ts-lg" id="ts-lg" v-model="to_language">
                <option value="ko">한국어</option>
                <option value="en">영어</option>
                <option value="ja">일본어</option>
                <option value="zh-CN">중국어</option>
                <option value="de">독일어</option>
                <option value="es">스페인어</option>
              </select>
            </div>
            <button @click="translation" class="ocr-trans-btn mt-4">
              번역하기
            </button>
          </div>
          <div v-show="isLogin">
            <div class="ppg-save-btn mt-4">
              <button @click="upload">저장하기</button>
            </div>
          </div>
        </div>
        <!--ts-output-cont-end-->
      </div>
      <!--ts-container-end-->
      <br />
      <div
        style="
          text-align: center;
          font-weight: bold;
          margin-top: 23px;
          margin-bottom: 23px;
        "
      >
        <p>[ Tip : 로그인을 하시면 자료를 보관하고 내려받을 수 있습니다 ]</p>
      </div>
    </div>
    <!--ocr-bottom-container-end-->
  </div>
  <!--field_end-->
</template>

<script>
// import $ from 'jquery'
import axios from "axios";

export default {
  name: "papagoPage",
  data() {
    return {
      googleAuth: null,
      image: "",
      text: "",
      from_language: "ko",
      to_language: "en",
    };
  },
  components: {},

  methods: {
    async upload() {
      if (this.image == null || this.image === "") {
        alert("데이터를 입력해주세요.");
        return;
      }
      let form = new FormData();
      form.append("email", this.$store.state.userInfo.email);
      var date = new Date();

      form.append(
        "trans_date",
        new Date(
          date.getTime() - date.getTimezoneOffset() * 60000
        ).toISOString()
      );
      form.append("kind", "images");
      form.append("input", this.$refs["image"].files[0]);
      form.append("output", this.text);
      this.$store.dispatch("setLoading", true);
      await axios
        .post("/api/ocr/upload", form, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => {
          console.log(res);
          alert("저장이 완료되었습니다.");
        })
        .catch((err) => {
          console.log("refreshToken error : ", err.config);
          alert("잘못된 요청입니다.");
        });
      this.$store.dispatch("setLoading", false);
    },

    async translation() {
      console.log(this.to_language);
      if (this.to_language == this.from_language) {
        alert("동일한 언어입니다.");
        return;
      }
      let form = new FormData();
      form.append("text", this.text);
      form.append("from_language", this.from_language);
      form.append("to_language", this.to_language);
      this.$store.dispatch("setLoading", true);
      await axios
        .post("/api/papago/json", form, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => {
          console.log(res);
          if (res.data.includes("errorCode:N2MT06")) {
            alert("지원하지 않는 번역기입니다.");
            return;
          }
          if (res.data.includes("errorCode:N2MT08")) {
            alert("번역기 용량이 초과되었습니다.");
            return;
          }
          if (res.data.includes("errorCode:010")) {
            alert("파파고 사용제한이 초과되었습니다.");
            return;
          }
          this.text = res.data;
          console.log(this.text);
        })
        .catch((err) => {
          console.log("refreshToken error : ", err.config);
        });
      this.$store.dispatch("setLoading", false);
    },
    async uploadImg() {
      var image = this.$refs["image"].files[0];
      if (image == null || image === "") {
        alert("잘못된 파일 형식입니다.");
        return;
      }
      const url = URL.createObjectURL(image);
      this.image = url;
      let form = new FormData();
      form.append("file", image);
      this.$store.dispatch("setLoading", true);
      await axios
        .post("/api/ocr", form, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => {
          console.log(res);
          this.text = res.data;
          console.log(this.text);
        })
        .catch((err) => {
          console.log("refreshToken error : ", err.config);
        });
      this.$store.dispatch("setLoading", false);
    },
  },
  mounted() {},
  computed: {
    content() {
      return this.text.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    isLogin() {
      return this.$store.state.isLogin;
    },
  },
};
</script>

<style>
body {
  margin: 0;
}

textarea {
  resize: none;
}

.title-name {
  align-items: center;
}

.profile-logo > div > h2 {
  color: white;
  font-weight: bold;
}

.ocr-bottom-container {
  background: white;
  border-radius: 100px 0px 0px 0px;
}

.ocr-ts-container {
  width: 100%;
  display: flex;
  justify-content: center;
}

.top-content {
  display: flex;
  justify-content: space-between;
  padding: 2rem 4rem 2rem 4rem;
  color: white;
}

.ocr-output-cont {
  width: 40%;
  margin: 1rem;
  padding: 2rem 3rem 2rem 3rem;
  border: 1px solid #dbdbdb;
  border-radius: 25px;
}

.ocr-ts-lg-ch {
  margin-right: 1rem;
  text-align: left;
}

.ocr-ts-lg-ch > select {
  font-size: 1rem;
  padding: 0.5rem;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
}

select:hover {
  border-color: none;
}

select:focus {
  outline: none;
}

.ocr-ts-input-cont {
  width: 40%;
  margin: 1rem;
  padding: 2rem 3rem 2rem 3rem;
  border: 1px solid #dbdbdb;
  border-radius: 25px;
  text-align: center;
}

.ocr-img-box {
  width: 100%;
  height: 400px;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  overflow: auto;
}

.ocr-img-box::-webkit-scrollbar {
  display: none;
}

.ocr-img-fit {
  width: 100%;
  border-radius: 10px;
  object-fit: contain;
}

.ocr-ts-box {
  width: 100%;
  height: 400px;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  padding: 1rem;
  text-align: left;
  overflow: auto;
}

.ocr-cf-btn {
  padding: 0.5rem;
  background: white;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  font-size: 1rem;
}

.ocr-trans-btn {
  width: 100%;
  margin-left: 1rem;
  padding: 0.5rem;
  color: white;
  background: #0d66ff;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  font-size: 1rem;
}

.ts-output-cont {
  width: 35%;
  margin: 1rem;
  padding: 2rem 3rem 2rem 3rem;
  border: 1px solid #dbdbdb;
  border-radius: 25px;
}
</style>