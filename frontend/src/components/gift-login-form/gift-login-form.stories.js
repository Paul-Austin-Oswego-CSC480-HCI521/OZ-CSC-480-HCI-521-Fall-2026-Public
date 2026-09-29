import { html } from "lit";
import "./gift-login-form.js";

export default {
  title: "Example/Login Form",
  render: ({ errorMessage, loading }) => html`
    <gift-login-form error-message=${errorMessage} ?loading=${loading}></gift-login-form>
  `,
  argTypes: {
    errorMessage: { control: "text" },
    loading: { control: "boolean" },
  },
  args: { errorMessage: "", loading: false },
};

export const Default = {};
export const Error = { args: { errorMessage: "Wrong email or password" } };
export const Loading = { args: { loading: true } };
