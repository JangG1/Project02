<template>
  <div class="field">
    <div class="top-container px-5 py-5">
      <div class="profile-logo">
        <div>
          <h2>매체번역</h2>
        </div>
        <div style="margin-left: 41%">
          <h2 @click="$router.push('/')">PAGO BOOKS</h2>
        </div>
      </div>
      <!--profile-logo-end-->
    </div>
    <!--top-container-end-->
    <div class="stt-bottom-container px-5 pt-5 pb-2">
      <div class="stt-ts-container">
        <div class="stt-ts-input-cont">
          <div>
            <form>
              <audio class="player" controls ref="player" style="display: none">
                <source src="" ref="source" />
              </audio>
              <video class="player2" controls ref="player2">
                <source src="" ref="source" />
              </video>
              <div class="stt-input-btn">
                <div class="stt-ts-lg-ch mt-4">
                  <select name="ts-lg" id="ts-lg" v-model="lang">
                    <option value="Kor">한국어</option>
                    <option value="Eng">영어</option>
                    <option value="Jpn">일본어</option>
                    <option value="Chn">중국어</option>
                  </select>
                </div>
                <div class="stt-cf-btn mt-4">
                  <label for="chooseFile">파일 가져오기</label>
                  <form method="post" enctype="multipart/form-data">
                    <input
                      ref="image"
                      @change="uploadImg()"
                      type="file"
                      id="chooseFile"
                      name="chooseFile"
                      accept="audio/* video/*"
                      style="display: none"
                    />
                  </form>
                </div>
              </div>
              <!-- <button type="button" @click="sendData()">전송</button> -->
            </form>
          </div>
        </div>
        <!--ts-input-cont-end-->

        <div class="stt-output-cont">
          <div class="stt-ts-box">
            <form action="#">
              <output name="result" for="text">
                <div v-html="content"></div>
              </output>
            </form>
          </div>

          <div style="display: flex">
            <div class="stt-ts-lg-ch mt-4">
              <select name="ts-lg" id="ts-lg" v-model="papagolang">
                <option value="ko">한국어</option>
                <option value="en">영어</option>
                <option value="ja">일본어</option>
                <option value="zh-CN">중국어</option>
                <option value="de">독일어</option>
                <option value="es">스페인어</option>
              </select>
            </div>
            <button @click="translation" class="stt-trans-btn mt-4">
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
          margin-top: 20px;
          margin-bottom: 20px;
        "
      >
        <p>[ Tip : 로그인을 하시면 자료를 보관하고 내려받을 수 있습니다 ]</p>
      </div>
    </div>
    <!--stt-bottom-container-end-->
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
      image: "",
      file: ``,
      lang: "Kor",
      text: "",
      papagolang: "en",
      showInput: false,
    };
  },
  components: {},
  methods: {
    async upload() {
      let form = new FormData();
      form.append("email", this.$store.state.userInfo.email);
      var date = new Date();

      form.append(
        "trans_date",
        new Date(
          date.getTime() - date.getTimezoneOffset() * 60000
        ).toISOString()
      );
      form.append("kind", "media");
      form.append("input", this.$refs["image"].files[0]);
      form.append("output", this.text);

      this.$store.dispatch("setLoading", true);
      await axios

        .post("/api/Stt/upload", form, {
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
        });
      this.$store.dispatch("setLoading", false);
    },

    async translation() {
      let form = new FormData();
      const temp = this.lang;
      if (this.lang == "Kor") {
        this.lang = "ko";
      }
      if (this.lang == "Eng") {
        this.lang = "en";
      }
      if (this.lang == "Jpn") {
        this.lang = "ja";
      }
      if (this.lang == "Chn") {
        this.lang = "zh-CN";
      }
      if (this.to_language == this.lang) {
        alert("동일한 언어입니다.");
        return;
      }
      if (this.text == "") {
        alert("공란입니다");
        return;
      }
      form.append("text", this.text);
      form.append("from_language", this.lang);
      form.append("to_language", this.papagolang);
      this.lang = temp;
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
        })
        .catch((err) => {
          console.log("refreshToken error : ", err.config);
        });
      this.$store.dispatch("setLoading", false);
    },
    async uploadImg() {
      this.$refs.source.src = "";
      var image = this.$refs["image"].files[0];
      if (image == null || image === "") {
        alert("잘못된 파일 형식입니다.");
        return;
      }
      const url = URL.createObjectURL(image);

      this.$refs.source.src = url;
      this.$refs.player.load();
      this.$refs.player2.load();
      //this.image = url;
      let form = new FormData();
      form.append("file", image);
      form.append("lang", this.lang);
      this.$store.dispatch("setLoading", true);
      await axios
        .post("/api/Stt", form, {
          headers: {
            "Content-Type": "multipart/form-data",
            Accept: "*/*",
          },
        })
        .then((res) => {
          console.log(res);
          console.log(res.data);
          console.log(res.data.text);
          this.text = res.data.text;
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

.profile-logo > div > h2 {
  color: white;
  font-weight: bold;
}

.stt-bottom-container {
  background: white;
  border-radius: 100px 0px 0px 0px;
}

.stt-ts-container {
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

.stt-input-btn {
  display: flex;
}

.stt-ts-lg-ch {
  margin-right: 1rem;
  text-align: left;
}

.stt-ts-lg-ch > select {
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

.stt-ts-input-cont {
  width: 40%;
  margin: 1rem;
  padding: 2rem 3rem 2rem 3rem;
  border: 1px solid #dbdbdb;
  border-radius: 25px;
  text-align: center;
}

.stt-ts-box {
  width: 100%;
  height: 400px;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  padding: 1rem;
  text-align: left;
  overflow: auto;
}

.stt-ts-box::-webkit-scrollbar {
  display: none;
}

.stt-cf-btn {
  width: 100%;
  padding: 0.5rem;
  background: white;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  font-size: 1rem;
}

.stt-trans-btn {
  width: 100%;
  margin-left: 1rem;
  padding: 0.5rem;
  color: white;
  background: #0d66ff;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  font-size: 1rem;
}
.stt-trans-btn > button {
  width: 100%;
  color: white;
  background: #0d66ff;
  border: none;
  border-radius: 10px;
  font-size: 1rem;
}
.stt-output-cont {
  width: 40%;
  margin: 1rem;
  padding: 2rem 3rem 2rem 3rem;
  border: 1px solid #dbdbdb;
  border-radius: 25px;
}
.output-record {
  text-align: end;
}
.output-record > button {
  text-align: center;
  width: 20%;
  padding: 1.25rem;
  background: #0d66ff;
  border-radius: 50px 0px 0px 0px;
}
.player {
  width: 100%;
}
.player2 {
  width: 100%;
  height: 400px;
  border-radius: 10px;
}
</style>
