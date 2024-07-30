module.exports = {
  env: {
    node: true,
    browser: true
  },
  extends: [
    "eslint:recommended",
    "plugin:vue/vue3-recommended",
    "prettier",
    "plugin:prettier/recommended"
  ],
  rules: {
    "vue/component-tags-order": [
      "error",
      {
        order: [["script", "template"], "style"]
      }
    ],
    "comma-dangle": ["error", "never"], // 후행컴마 x
    // "no-console": "warn", // console.log 사용 시 경고
    "no-unused-vars": "warn", // 할당되지 않은 변수 있을 시 경고 2/2/2 필수
    "vue/multi-word-component-names": "off" // 싱글 컴포넌트 네이밍 허용
  }
};