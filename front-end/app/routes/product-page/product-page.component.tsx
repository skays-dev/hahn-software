import { useState } from "react";
import { Outlet, useNavigate } from "react-router";
import ProductList from "../../components/products-list/product-list.component";

const ProductPage = () => {
  const navigate = useNavigate();

  return (
    <div className="container" style={{width: "100%", height: "100vh"}}>
      <header className="flex justify-between">
        <h3>Products Management</h3>
        <div style={{ marginTop: "30px", marginBottom: "18px" }}>
          <button onClick={() => navigate("/products/new")}>Add</button>
        </div>
      </header>

      <ProductList/>
      <Outlet />
    </div>
  );
};

export default ProductPage;
