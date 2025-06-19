import { route, index, type RouteConfig } from "@react-router/dev/routes";

export default [
  index("./routes/index/index.tsx"),
  route("products", "./routes/product-page/product-page.component.tsx"),
  route("products/new", "./routes/add-edit-product/add-edit-product.component.tsx", {
    id: "add-product",
  }),
  route("products/:id/edit", "./routes/add-edit-product/add-edit-product.component.tsx", {
    id: "edit-product",
  }),
] satisfies RouteConfig;

