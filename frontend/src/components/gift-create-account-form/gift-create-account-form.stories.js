import { html } from "lit";
import "./gift-create-account-form.js";

export default {
  title: "Custom Components/Create Account Form",
  render: ({ errorMessage, loading }) => html`
    <gift-create-account-form
      error-message=${errorMessage}
      ?loading=${loading}
    ></gift-create-account-form>
  `,
  argTypes: {
    errorMessage: { control: "text" },
    loading: { control: "boolean" },
  },
  args: { errorMessage: "", loading: false },
};
export const Default = {};
export const Error = {
  args: { errorMessage: "Unable to create your account. Please try again." },
};
export const Loading = { args: { loading: true } };
